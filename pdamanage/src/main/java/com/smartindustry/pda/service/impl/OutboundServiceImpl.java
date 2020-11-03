package com.smartindustry.pda.service.impl;

import com.smartindustry.common.bo.om.OutboundBodyBO;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.om.OutboundBodyMapper;
import com.smartindustry.common.mapper.om.OutboundForkliftMapper;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.sm.StorageBodyMapper;
import com.smartindustry.common.mapper.sm.StorageDetailMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import com.smartindustry.common.pojo.om.OutboundForkliftPO;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.sm.StorageDetailPO;
import com.smartindustry.common.util.DateUtil;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.constant.OutboundConstant;
import com.smartindustry.pda.constant.StorageConstant;
import com.smartindustry.pda.dto.OutboundDTO;
import com.smartindustry.pda.service.IOutboundService;
import com.smartindustry.pda.socket.WebSocketServer;
import com.smartindustry.pda.util.OutboundNoUtil;
import com.smartindustry.pda.vo.OutboundDetailVO;
import com.smartindustry.pda.vo.PdaListVO;
import com.smartindustry.pda.vo.WebSocketVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: xiahui
 * @date: Created in 2020/10/29 9:28
 * @description: 成品出库
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class OutboundServiceImpl implements IOutboundService {
    @Autowired
    private OutboundHeadMapper outboundHeadMapper;
    @Autowired
    private OutboundBodyMapper outboundBodyMapper;
    @Autowired
    private StorageHeadMapper storageHeadMapper;
    @Autowired
    private StorageBodyMapper storageBodyMapper;
    @Autowired
    private StorageDetailMapper storageDetailMapper;
    @Autowired
    private ForkliftMapper forkliftMapper;
    @Autowired
    private OutboundForkliftMapper outboundForkliftMapper;
    @Autowired
    private LocationMapper locationMapper;

    /**
     * ERP 生成 出库单
     *
     * @return
     */
    @Override
    public ResultVO erp() {
        // 出库表头
        OutboundHeadPO headPO = new OutboundHeadPO();
        headPO.setOutboundNo(OutboundNoUtil.genOutboundHeadNo(outboundHeadMapper, OutboundNoUtil.OUTBOUND_HEAD_XS, new Date()));
        headPO.setSourceNo("XS" + DateUtil.date2Str(new Date(), DateUtil.YMDHMS));
        headPO.setSourceType((byte) 3);
        headPO.setExpectNum(new BigDecimal(18));
        headPO.setOutboundNum(BigDecimal.ZERO);
        headPO.setStatus((byte) 3);
        headPO.setCreateTime(new Date());
        headPO.setDr((byte) 1);
        outboundHeadMapper.insert(headPO);

        // 出库表体
        OutboundBodyPO bodyPO1 = new OutboundBodyPO();
        bodyPO1.setOutboundHeadId(headPO.getOutboundHeadId());
        bodyPO1.setMaterialId(1L);
        bodyPO1.setExpectNum(new BigDecimal(6));
        bodyPO1.setCreateTime(new Date());
        bodyPO1.setDr((byte) 1);
        outboundBodyMapper.insert(bodyPO1);

        OutboundBodyPO bodyPO2 = new OutboundBodyPO();
        bodyPO2.setOutboundHeadId(headPO.getOutboundHeadId());
        bodyPO2.setMaterialId(2L);
        bodyPO2.setExpectNum(new BigDecimal(12));
        bodyPO2.setCreateTime(new Date());
        bodyPO2.setDr((byte) 1);
        outboundBodyMapper.insert(bodyPO2);

        return ResultVO.ok();
    }

    /**
     * 上线
     *
     * @param session
     * @param dto
     * @return
     */
    @Override
    public ResultVO online(HttpSession session, OutboundDTO dto) {
        if (StringUtils.isEmpty(dto.getImei())) {
            return new ResultVO(1001);
        }

        // 叉车信息
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(dto.getImei());
        if (null == forkliftPO) {
            return new ResultVO(1002);
        }

        session.setAttribute(OutboundConstant.SESSION_IMEI, dto.getImei());
        session.setMaxInactiveInterval(30 * 24 * 60 * 60);

        return ResultVO.ok().setData(forkliftPO.getForkliftName());
    }

    /**
     * 列表区域
     *
     * @param session
     * @param type
     * @return
     */
    @Override
    public ResultVO list(HttpSession session, Byte type) {
        if (null == type) {
            return new ResultVO(1001);
        }

        String imei = (String) session.getAttribute(OutboundConstant.SESSION_IMEI);
        if (null == imei) {
            return new ResultVO(1111);
        }

        // 叉车信息
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        if (null == forkliftPO) {
            return new ResultVO(1002);
        }

        PdaListVO vo = new PdaListVO();
        vo.setType(forkliftPO.getWorkArea());

        if (type != (byte) 4) {
            // 出库信息
            List<OutboundHeadBO> headBOs = outboundHeadMapper.queryPdaByType(type);

            Set<Long> ohids = new HashSet<>();
            for (OutboundHeadBO headBO : headBOs) {
                ohids.add(headBO.getOutboundHeadId());
            }
            session.setAttribute(OutboundConstant.SESSION_OHIDS, ohids);

            vo.setOlist(headBOs);
        }

        // 入库信息
        List<StorageHeadBO> storageHeadBOS = storageHeadMapper.queryPdaByType(type);

        Set<Long> shids = new HashSet<>();
        for (StorageHeadBO storageHeadBO : storageHeadBOS) {
            shids.add(storageHeadBO.getStorageHeadId());
        }
        session.setAttribute(StorageConstant.SESSION_SHIDS, shids);
        vo.setSlist(storageHeadBOS);

        return ResultVO.ok().setData(vo);
    }

    /**
     * 详情
     *
     * @param session
     * @param dto
     * @return
     */
    @Override
    public ResultVO detail(HttpSession session, OutboundDTO dto) {
        if (null == dto.getOhid()) {
            return new ResultVO(1001);
        }

        String imei = (String) session.getAttribute(OutboundConstant.SESSION_IMEI);
        if (null == imei) {
            return new ResultVO(1111);
        }

        // 出库信息
        OutboundHeadBO headBO = outboundHeadMapper.queryByOhid(dto.getOhid());
        if (null == headBO) {
            return new ResultVO(1002);
        }
        OutboundDetailVO vo = OutboundDetailVO.convert(headBO);

        // 储位图
        Map<Long, OutboundDetailVO.LocationVO> lvos = new HashMap<>();
        for (OutboundBodyBO bo : headBO.getBodyBOs()) {
            OutboundDetailVO.LocationVO lvo = new OutboundDetailVO.LocationVO();
            lvo.setColor(OutboundDetailVO.COLORS[lvos.size()]);
            lvo.setMinfo(bo.getMaterialName() + " " + bo.getMaterialModel());
            lvos.put(bo.getMaterialId(), lvo);
        }
        List<LocationPO> locationPOs = locationMapper.queryByMids(new ArrayList<>(lvos.keySet()));
        for (LocationPO locationPO : locationPOs) {
            OutboundDetailVO.LocationVO lvo = lvos.get(locationPO.getMaterialId());
            lvo.getLrfids().add(locationPO.getLocationNo());
        }
        vo.setLvos(new ArrayList<>(lvos.values()));

        // 叉车信息
        List<ForkliftPO> pos = forkliftMapper.queryByOhid(dto.getOhid());
        if (null != pos && pos.size() > 0) {
            vo.setStatus("辅助执行");

            List<String> fnames = new ArrayList<>(pos.size());
            for (ForkliftPO po : pos) {
                fnames.add(po.getForkliftNo());
                if (imei.equals(po.getImeiNo())) {
                    vo.setStatus("关闭");
                }
            }
            vo.setFnames(fnames);
        } else {
            vo.setStatus("开始执行");
        }
        vo.setCnum(headBO.getOutboundNum().add(BigDecimal.valueOf(null == pos ? 0 : pos.size())));

        session.setAttribute(OutboundConstant.SESSION_OHID, dto.getOhid());

        return ResultVO.ok().setData(vo);
    }

    /**
     * 执行
     *
     * @param session
     * @return
     */
    @Override
    public ResultVO execute(HttpSession session) {
        String imei = (String) session.getAttribute(OutboundConstant.SESSION_IMEI);
        if (null == imei) {
            return new ResultVO(1111);
        }

        Long ohid = (Long) session.getAttribute(OutboundConstant.SESSION_OHID);
        if (null == ohid) {
            return new ResultVO(1002);
        }
        ForkliftPO fPO = forkliftMapper.queryByImei(imei);

        List<ForkliftPO> pos = forkliftMapper.queryByOhid(ohid);
        List<String> fnames = new ArrayList<>();
        if (null == pos || pos.size() == 0) {
            // 开始任务
            OutboundForkliftPO ofPO = new OutboundForkliftPO();
            ofPO.setForkliftId(fPO.getForkliftId());
            ofPO.setOutboundHeadId(ohid);
            outboundForkliftMapper.insert(ofPO);

            session.setAttribute(OutboundConstant.SESSION_OHID, ohid);

            // 发送 websocket
            fnames.add(fPO.getForkliftName());
            sendOutboundMsg(ohid, fnames);

            return ResultVO.ok();
        }

        boolean close = false;
        for (ForkliftPO po : pos) {
            if (imei.equals(po.getImeiNo())) {
                // 关闭
                outboundForkliftMapper.deleteByFid(po.getForkliftId());
                session.removeAttribute(OutboundConstant.SESSION_OHID);
                close = true;
            } else {
                fnames.add(po.getForkliftName());
            }
        }

        if (!close) {
            // 辅助任务
            OutboundForkliftPO ofPO = new OutboundForkliftPO();
            ofPO.setForkliftId(fPO.getForkliftId());
            ofPO.setOutboundHeadId(ohid);
            outboundForkliftMapper.insert(ofPO);

            fnames.add(fPO.getForkliftName());

            session.setAttribute(OutboundConstant.SESSION_OHID, ohid);
        }

        sendOutboundMsg(ohid, fnames);

        return ResultVO.ok();
    }

    /**
     * 出库
     *
     * @param session
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO rfid(HttpSession session, OutboundDTO dto) {
        if (null == dto.getMrfid() && null == dto.getLrfid()) {
            return new ResultVO(1001);
        }

        // 当前叉车信息
        String imei = (String) session.getAttribute(OutboundConstant.SESSION_IMEI);
        if (null == imei) {
            return new ResultVO(1111);
        }

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
                // 砧板入库
                ofPO.setRfid(null);
                outboundForkliftMapper.updateByPrimaryKey(ofPO);

                detailPO.setStorageStatus((byte) 2);
                storageDetailMapper.updateByPrimaryKey(detailPO);

                OutboundHeadPO headPO = outboundHeadMapper.selectByPrimaryKey(ofPO.getOutboundHeadId());
                headPO.setOutboundNum(headPO.getOutboundNum().add(BigDecimal.ONE));
                if(headPO.getExpectNum().equals(headPO.getOutboundNum())) {
                    // 入库完成
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
     * WebSocket 发送信息
     */
    private void sendOutboundMsg(Long ohid, List<String> fnames) {
        WebSocketVO vo = new WebSocketVO();
        WebSocketVO.OutboundVO ovo = new WebSocketVO.OutboundVO();
        OutboundHeadPO headPO = outboundHeadMapper.selectByPrimaryKey(ohid);
        ovo.setId(ohid);
        ovo.setSnum(headPO.getOutboundNum());
        ovo.setFnames(fnames);
        ovo.setCnum(headPO.getOutboundNum().add(BigDecimal.valueOf(fnames.size())));
        vo.setOvo(ovo);
        WebSocketServer.sendAllMsg(vo);
    }
}
