package com.smartindustry.pda.service.impl;

import com.smartindustry.common.bo.om.OutboundForkliftBO;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.om.OutboundBodyMapper;
import com.smartindustry.common.mapper.om.OutboundForkliftMapper;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.sm.StorageDetailMapper;
import com.smartindustry.common.mapper.sm.StorageForkliftMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import com.smartindustry.common.pojo.om.OutboundForkliftPO;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.sm.StorageDetailPO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.constant.CommonConstant;
import com.smartindustry.pda.dto.CommonDTO;
import com.smartindustry.pda.service.ICommonService;
import com.smartindustry.pda.socket.WebSocketServer;
import com.smartindustry.pda.vo.PdaListVO;
import com.smartindustry.pda.vo.WebSocketVO;
import org.apache.ibatis.javassist.tools.web.Webserver;
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
    private LocationMapper locationMapper;
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
        if (CommonConstant.STATUS_FORKLIFT_RFID_NONE.equals(status)) {
            return ResultVO.ok();
        }

        ForkliftPO fPO = forkliftMapper.queryByImei(imei);
        if (null == fPO) {
            return new ResultVO(1002);
        }
        if (CommonConstant.STATUS_FORKLIFT_RFID_STORAGE_FORKLIFT.equals(status)) {
            // 入库，叉起物料
            String mrfid = dto.getMrfid();

            // TODO: 业务逻辑

            return ResultVO.ok();
        }
        if (CommonConstant.STATUS_FORKLIFT_RFID_STORAGE_DONE.equals(status)) {
            // 入库
            String mrfid = (String) session.getAttribute(CommonConstant.SESSION_MRFID);
            String lrfid = dto.getLrfid();

            // TODO: 业务逻辑

            session.removeAttribute(CommonConstant.SESSION_MRFID);
            return ResultVO.ok();
        }

        OutboundForkliftPO outboundForkliftPO = outboundForkliftMapper.queryByFid(fPO.getForkliftId());
        if (CommonConstant.STATUS_FORKLIFT_RFID_OUTBOUND_FORKLIFT.equals(status)) {
            // 出库，叉起物料
            outboundForkliftPO.setRfid(dto.getMrfid());
            outboundForkliftMapper.updateByPrimaryKey(outboundForkliftPO);

            StorageDetailPO detailPO = storageDetailMapper.queryByRfidAndStatus(dto.getMrfid(), CommonConstant.STATUS_RFID_STORAGE);
            detailPO.setStorageStatus(CommonConstant.STATUS_RFID_STORAGE);
            storageDetailMapper.updateByPrimaryKey(detailPO);

            return ResultVO.ok();
        }
        if (CommonConstant.STATUS_FORKLIFT_RFID_OUTBOUND_RETURN.equals(status)) {
            // 出库，物料放回原位
            outboundForkliftPO.setRfid(null);
            outboundForkliftMapper.updateByPrimaryKey(outboundForkliftPO);

            StorageDetailPO detailPO = storageDetailMapper.queryByRfidAndStatus((String) session.getAttribute(CommonConstant.SESSION_MRFID), CommonConstant.STATUS_RFID_OUTBOUND_SALE);
            detailPO.setStorageStatus(CommonConstant.STATUS_RFID_STORAGE);
            storageDetailMapper.updateByPrimaryKey(detailPO);

            session.removeAttribute(CommonConstant.SESSION_MRFID);

            return ResultVO.ok();
        }
        if (CommonConstant.STATUS_FORKLIFT_RFID_OUTBOUND_DONE.equals(status)) {
            // 出库
            outboundForkliftPO.setRfid(null);
            outboundForkliftMapper.updateByPrimaryKey(outboundForkliftPO);

            StorageDetailPO detailPO = storageDetailMapper.queryByRfidAndStatus((String) session.getAttribute(CommonConstant.SESSION_MRFID), CommonConstant.STATUS_RFID_OUTBOUND_SALE);
            detailPO.setStorageStatus(CommonConstant.STATUS_RFID_OUTBOUND_DONE);
            storageDetailMapper.updateByPrimaryKey(detailPO);

            OutboundHeadPO headPO = outboundHeadMapper.selectByPrimaryKey(outboundForkliftPO.getOutboundHeadId());
            headPO.setOutboundNum(headPO.getOutboundNum().add(BigDecimal.ONE));
            if (headPO.getExpectNum().equals(headPO.getOutboundNum())) {
                // 出库完成
                headPO.setStatus((byte) 1);
                headPO.setOutboundTime(new Date());
                // 发送消息
                List<OutboundForkliftBO> ofBOs = outboundForkliftMapper.queryByOhid(headPO.getOutboundHeadId());
                List<String> imeis = new ArrayList<>();
                for (OutboundForkliftBO ofBO : ofBOs) {
                    if (!imei.equals(ofBO.getImeiNo())) {
                        imeis.add(ofBO.getImeiNo());
                    }
                }

                // websocket
                WebSocketVO vo = WebSocketVO.createTitleVO("业务单号：" + headPO.getSourceNo() + "（销售出库），已完成作业任务，任务关闭", CommonConstant.TYPE_TITLE_INTO);
                WebSocketVO.OutboundVO ovo = new WebSocketVO.OutboundVO();
                ovo.setCnum(headPO.getOutboundNum());
                ovo.setSnum(headPO.getOutboundNum());
                ovo.setId(headPO.getOutboundHeadId());
                ovo.setFnames(new ArrayList<>());
                ovo.setType(CommonConstant.TYPE_LIST_DONE);
                ovo.setStatus(CommonConstant.FLAG_OUTBOUND);
                vo.setOvo(ovo);
                WebSocketServer.sendMsg(imeis, vo);
            }

            outboundHeadMapper.updateByPrimaryKey(headPO);

            OutboundBodyPO bodyPO = outboundBodyMapper.queryByOhidAndMid(outboundForkliftPO.getOutboundHeadId(), detailPO.getMaterialId());
            bodyPO.setOutboundNum(bodyPO.getOutboundNum().add(BigDecimal.ONE));
            if (bodyPO.getExpectNum().equals(bodyPO.getOutboundNum())) {
                bodyPO.setOutboundTime(new Date());
            }
            outboundBodyMapper.updateByPrimaryKey(bodyPO);

            session.removeAttribute(CommonConstant.SESSION_MRFID);
            return ResultVO.ok();
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
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if ("ERROR".equals(dto.getMrfid()) || "ERROR".equals(dto.getLrfid())) {
            WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("当前阅读器或串口线故障，请检查！", CommonConstant.TYPE_TITLE_ERROR));
            return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
        }

        Byte status = (Byte) session.getAttribute(CommonConstant.SESSION_STATUS_FORKLIFT);
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
                return CommonConstant.STATUS_FORKLIFT_RFID_STORAGE_FORKLIFT;
            }

            // 出库
            WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("该物料无法识别，请停止操作！", CommonConstant.TYPE_TITLE_WARN));
            return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
        }
        if (CommonConstant.STATUS_FORKLIFT_WORK_STORAGE.equals(status)) {
            // 入库
            if (null != dto.getMrfid()) {
                return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
            }
            if (null == dto.getLrfid()) {
                WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("作业错误，成品摆放错误或丢失，请立即处理！", CommonConstant.TYPE_TITLE_WARN));
                return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
            }

            session.removeAttribute(CommonConstant.SESSION_STATUS_FORKLIFT);
            return CommonConstant.STATUS_FORKLIFT_RFID_STORAGE_DONE;
        }
        if (CommonConstant.STATUS_FORKLIFT_WORK_OUTBOUND_ONE.equals(status)) {
            // 出库 - 叉起货物
            if (null == dto.getMrfid()) {
                return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
            }

            StorageDetailPO detailPO = storageDetailMapper.queryByRfidAndStatus(dto.getMrfid(), CommonConstant.STATUS_RFID_STORAGE);
            if (null == detailPO) {
                WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("该物料RFID不在库中", CommonConstant.TYPE_TITLE_WARN));
                return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
            }

            Long ohid = (Long) session.getAttribute(CommonConstant.SESSION_OHID);
            OutboundBodyPO bodyPO = outboundBodyMapper.queryByOhidAndMid(ohid, detailPO.getMaterialId());
            if (null == bodyPO) {
                WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("该物料RFID不属于当前出库单", CommonConstant.TYPE_TITLE_WARN));
                return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
            }
            if (bodyPO.getExpectNum().equals(bodyPO.getOutboundNum())) {
                WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("该物料RFID在当前出库单中，但已被完成", CommonConstant.TYPE_TITLE_WARN));
                return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
            }

            session.setAttribute(CommonConstant.SESSION_STATUS_FORKLIFT, CommonConstant.STATUS_FORKLIFT_WORK_OUTBOUND_TWO);
            session.setAttribute(CommonConstant.SESSION_MRFID, dto.getMrfid());

            return CommonConstant.STATUS_FORKLIFT_RFID_OUTBOUND_FORKLIFT;
        }
        if (CommonConstant.STATUS_FORKLIFT_WORK_OUTBOUND_TWO.equals(status)) {
            // 出库
            if (null != dto.getMrfid()) {
                return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
            }

            if (null != dto.getLrfid()) {
                StorageDetailPO detailPO = storageDetailMapper.queryByRfidAndStatus((String) session.getAttribute(CommonConstant.SESSION_MRFID), CommonConstant.STATUS_RFID_OUTBOUND_SALE);
                LocationPO locationPO = locationMapper.selectByPrimaryKey(detailPO.getLocationId());
                if (dto.getLrfid().equals(locationPO.getLocationNo())) {
                    // 物料放回原位
                    session.setAttribute(CommonConstant.SESSION_STATUS_FORKLIFT, CommonConstant.STATUS_FORKLIFT_WORK_OUTBOUND_ONE);
                    return CommonConstant.STATUS_FORKLIFT_RFID_OUTBOUND_RETURN;
                } else {
                    WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("还未到出库区，物料RFID丢失", CommonConstant.TYPE_TITLE_WARN));
                }

                return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
            }

            session.setAttribute(CommonConstant.SESSION_STATUS_FORKLIFT, CommonConstant.STATUS_FORKLIFT_WORK_OUTBOUND_ONE);

            return CommonConstant.STATUS_FORKLIFT_RFID_OUTBOUND_DONE;
        }

        return CommonConstant.STATUS_FORKLIFT_RFID_NONE;
    }
}
