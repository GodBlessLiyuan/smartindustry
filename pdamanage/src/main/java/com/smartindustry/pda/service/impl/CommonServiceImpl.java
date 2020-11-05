package com.smartindustry.pda.service.impl;

import com.smartindustry.common.bo.om.OutboundForkliftBO;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.om.OutboundBodyMapper;
import com.smartindustry.common.mapper.om.OutboundForkliftMapper;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.mapper.sm.StorageDetailMapper;
import com.smartindustry.common.mapper.sm.StorageForkliftMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import com.smartindustry.common.pojo.om.OutboundForkliftPO;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.pojo.sm.StorageDetailPO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.constant.CommonConstant;
import com.smartindustry.pda.constant.StorageConstant;
import com.smartindustry.pda.dto.CommonDTO;
import com.smartindustry.pda.service.ICommonService;
import com.smartindustry.pda.socket.WebSocketServer;
import com.smartindustry.pda.vo.PdaListVO;
import com.smartindustry.pda.vo.WebSocketVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: xiahui
 * @date: Created in 2020/11/4 18:32
 * @description: 公共模块
 * @version: 1.0
 */
@Service
public class CommonServiceImpl implements ICommonService {

    @Autowired
    private ForkliftMapper forkliftMapper;
    @Autowired
    private OutboundHeadMapper outboundHeadMapper;
    @Autowired
    private OutboundBodyMapper outboundBodyMapper;
    @Autowired
    private OutboundForkliftMapper outboundForkliftMapper;
    @Autowired
    private StorageHeadMapper storageHeadMapper;
    @Autowired
    private StorageDetailMapper storageDetailMapper;
    @Autowired
    private StorageForkliftMapper storageForkliftMapper;

    /**
     * 上线
     *
     * @param session
     * @param dto
     * @return
     */
    @Override
    public ResultVO online(HttpSession session, CommonDTO dto) {
        if (StringUtils.isEmpty(dto.getImei())) {
            return new ResultVO(1001);
        }

        // 叉车信息
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(dto.getImei());
        if (null == forkliftPO) {
            return new ResultVO(1002);
        }

        session.setAttribute(CommonConstant.SESSION_IMEI, dto.getImei());
        session.setMaxInactiveInterval(30 * 24 * 60 * 60);

        return ResultVO.ok().setData(forkliftPO.getForkliftName());
    }

    /**
     * 出入库列表区域
     *
     * @param session
     * @param dto     列表类型：1-待执行；2-执行中；3-已执行；4-未完成生产
     * @return
     */
    @Override
    public ResultVO list(HttpSession session, CommonDTO dto) {
        if (null == dto.getType()) {
            return new ResultVO(1001);
        }

        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if (null == imei) {
            return new ResultVO(1111);
        }

        // 叉车信息
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        if (null == forkliftPO) {
            return new ResultVO(1002);
        }

        // 列表信息
        PdaListVO vo = new PdaListVO();
        vo.setType(forkliftPO.getWorkArea());

        if (!CommonConstant.TYPE_LIST_UNDONE.equals(dto.getType())) {
            // 出库信息
            List<OutboundHeadBO> headBOs = outboundHeadMapper.queryOlistByPdaType(dto.getType());
            if (CommonConstant.TYPE_LIST_DOING.equals(dto.getType())) {
                // 执行中需要计算当前数量
                List<Long> hids = new ArrayList<>(headBOs.size());
                for (OutboundHeadBO headBO : headBOs) {
                    hids.add(headBO.getOutboundHeadId());
                }
                if (hids.size() != 0) {
                    Map<Long, Integer> fnumMap = outboundForkliftMapper.queryFnumByHids(hids);
                    for (OutboundHeadBO headBO : headBOs) {
                        headBO.setExpectNum(headBO.getExpectNum().add(BigDecimal.valueOf(fnumMap.get(headBO.getOutboundHeadId()))));
                    }
                }
            }
            vo.setOlist(headBOs);
        }

        // 入库信息
        List<StorageHeadBO> storageHeadBOS = storageHeadMapper.queryPdaByType(dto.getType());
        if (CommonConstant.TYPE_LIST_DOING.equals(dto.getType())) {
            // 执行中需要计算当前数量
            List<Long> sids = new ArrayList<>(storageHeadBOS.size());
            for (StorageHeadBO headBO : storageHeadBOS) {
                sids.add(headBO.getStorageHeadId());
            }
            if (sids.size() != 0) {
                Map<Long, Integer> fnumMap = storageForkliftMapper.queryFnumBySids(sids);
                for (StorageHeadBO headBO : storageHeadBOS) {
                    headBO.setStorageNum(headBO.getStorageNum().add(BigDecimal.valueOf(fnumMap.get(headBO.getStorageHeadId()))));
                }
            }
        }
        vo.setSlist(storageHeadBOS);

        return ResultVO.ok().setData(vo);
    }

    /**
     * 数采 RFID
     *
     * @param session
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO rfid(HttpSession session, CommonDTO dto) {
        // 当前叉车信息
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if (null == imei) {
            return new ResultVO(1111);
        }

        Byte status = this.checkRfids(session, dto);


        ForkliftPO fPO = forkliftMapper.queryByImei(imei);
        if (null == fPO) {
            return new ResultVO(1002);
        }

        OutboundForkliftPO ofPO = outboundForkliftMapper.queryByFid(fPO.getForkliftId());
        if (null == ofPO) {
            // 尚未开始任务
            WebSocketServer.sendMsg(imei, "请先开始任务！");
            return new ResultVO(1003);
        }

        StorageDetailPO detailPO = storageDetailMapper.queryByRfidAndStatus(dto.getMrfid(), (byte) 1);
        if (null == detailPO) {
            // 入库
        } else {
            // 出库
            if (null == ofPO.getRfid()) {
                // 叉起砧板
                ofPO.setRfid(dto.getMrfid());
                outboundForkliftMapper.updateByPrimaryKey(ofPO);
            } else {
                // 砧板出库
                ofPO.setRfid(null);
                outboundForkliftMapper.updateByPrimaryKey(ofPO);

                detailPO.setStorageStatus((byte) 2);
                storageDetailMapper.updateByPrimaryKey(detailPO);

                OutboundHeadPO headPO = outboundHeadMapper.selectByPrimaryKey(ofPO.getOutboundHeadId());
                headPO.setOutboundNum(headPO.getOutboundNum().add(BigDecimal.ONE));
                if (headPO.getExpectNum().equals(headPO.getOutboundNum())) {
                    // 出库完成
                    headPO.setStatus((byte) 1);
                    // 发送消息
                    List<OutboundForkliftBO> ofBOs = outboundForkliftMapper.queryByOhid(headPO.getOutboundHeadId());
                    List<String> imeis = new ArrayList<>();
                    for (OutboundForkliftBO ofBO : ofBOs) {
                        if (!imei.equals(ofBO.getImeiNo())) {
                            imeis.add(ofBO.getImeiNo());
                        }
                    }
                    WebSocketVO vo = new WebSocketVO();
                    WebSocketVO.TitleVO titleVO = new WebSocketVO.TitleVO();
                    titleVO.setMsg("当前订单已完成");
                    WebSocketServer.sendMsg(imeis, vo);
                }
                outboundHeadMapper.updateByPrimaryKey(headPO);
                OutboundBodyPO bodyPO = outboundBodyMapper.queryByOhidAndMid(ofPO.getOutboundHeadId(), detailPO.getMaterialId());
                bodyPO.setOutboundNum(bodyPO.getOutboundNum().add(BigDecimal.ONE));
                outboundBodyMapper.updateByPrimaryKey(bodyPO);
            }
        }

        return ResultVO.ok();
    }

    /**
     * 数采预处理
     *
     * @param session
     * @param dto
     * @return
     */
    private Byte checkRfids(HttpSession session, CommonDTO dto) {
        Byte status = (Byte) session.getAttribute(CommonConstant.SESSION_STATUS_FORKLIFT);
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);

        if (null == status) {
            // 无状态
            if (null == dto.getMrfid()) {
                return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
            }

            StorageDetailPO detailPO = storageDetailMapper.queryByRfidAndStatus(dto.getMrfid(), CommonConstant.STATUS_RFID_STORAGE);
            if (null == detailPO) {
                // 入库
                session.setAttribute(CommonConstant.SESSION_STATUS_FORKLIFT, CommonConstant.STATUS_FORKLIFT_WORK_STORAGE);
                session.setAttribute(CommonConstant.SESSION_MRFID, dto.getMrfid());
                return CommonConstant.STATUS_FORKLIFT_RFID_FORKLIFT;
            }

            // 出库
            WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("找不到该物料RFID"));
            return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
        }
        if (CommonConstant.STATUS_FORKLIFT_WORK_STORAGE.equals(status)) {
            // 入库
            if (null != dto.getMrfid()) {
                return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
            }
            if (null == dto.getLrfid()) {
                WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("没有储位RFID"));
                return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
            }

            session.removeAttribute(CommonConstant.SESSION_STATUS_FORKLIFT);
            return CommonConstant.STATUS_FORKLIFT_RFID_STORAGE;
        }
        if (CommonConstant.STATUS_FORKLIFT_WORK_OUTBOUND.equals(status)) {
            // 出库
        }

        return 1;
    }
}
