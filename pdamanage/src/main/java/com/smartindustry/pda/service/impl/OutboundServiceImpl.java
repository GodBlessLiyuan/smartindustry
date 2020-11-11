package com.smartindustry.pda.service.impl;

import com.smartindustry.common.bo.om.OutboundBodyBO;
import com.smartindustry.common.bo.om.OutboundForkliftBO;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.mapper.om.OutboundBodyMapper;
import com.smartindustry.common.mapper.om.OutboundForkliftMapper;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import com.smartindustry.common.pojo.om.OutboundForkliftPO;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.util.DateUtil;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.config.RfidConfig;
import com.smartindustry.pda.constant.CommonConstant;
import com.smartindustry.pda.constant.OutboundConstant;
import com.smartindustry.pda.dto.OutboundDTO;
import com.smartindustry.pda.service.IOutboundService;
import com.smartindustry.pda.socket.WebSocketServer;
import com.smartindustry.pda.util.OutboundNoUtil;
import com.smartindustry.pda.vo.OutboundDetailVO;
import com.smartindustry.pda.socket.WebSocketVO;
import com.smartindustry.pda.vo.StorageDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

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
    private ForkliftMapper forkliftMapper;
    @Autowired
    private OutboundForkliftMapper outboundForkliftMapper;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private RfidConfig rfidConfig;

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

        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if (null == imei) {
            return new ResultVO(1111);
        }

        // 出库信息
        OutboundHeadBO headBO = outboundHeadMapper.queryByOhid(dto.getOhid());
        if (null == headBO) {
            return new ResultVO(1002);
        }
        OutboundDetailVO vo = OutboundDetailVO.convert(headBO);
        if (headBO.getStatus() == 1) {
            // 入库完成
            vo.setCnum(headBO.getOutboundNum());
            vo.setStatus(OutboundConstant.STATUS_OUTBOUND_VANISH);
            return ResultVO.ok().setData(vo);
        }

        // 储位图
        Map<Long, OutboundDetailVO.LocationVO> lvos = new HashMap<>();
        for (OutboundBodyBO bo : headBO.getBodyBOs()) {
            OutboundDetailVO.LocationVO lvo = new OutboundDetailVO.LocationVO();
            lvo.setColor(CommonConstant.COLORS[lvos.size()]);
            lvo.setMinfo(bo.getMaterialName() + " " + bo.getMaterialModel());
            lvos.put(bo.getMaterialId(), lvo);
        }
        Map<String, String> rfidMaps = rfidConfig.parseMap(rfidConfig.getMaps());
        if (lvos.size() > 0) {
            List<LocationPO> locationPOs = locationMapper.queryByMids(new ArrayList<>(lvos.keySet()));
            for (LocationPO locationPO : locationPOs) {
                OutboundDetailVO.LocationVO lvo = lvos.get(locationPO.getMaterialId());
                for (Map.Entry<String, String> entry : rfidMaps.entrySet()) {
                    if (entry.getKey().equals(locationPO.getLocationNo())) {
                        lvo.getLrfids().add(entry.getValue());
                    }
                }
            }
            vo.setLvos(new ArrayList<>(lvos.values()));
        }

        // 叉车信息
        List<OutboundForkliftBO> ofBO = outboundForkliftMapper.queryByOhid(dto.getOhid());
        if (null != ofBO && ofBO.size() > 0) {
            vo.setStatus(OutboundConstant.STATUS_OUTBOUND_ASSIST);
            for (OutboundForkliftBO bo : ofBO) {
                if (imei.equals(bo.getImeiNo())) {
                    vo.setStatus(OutboundConstant.STATUS_OUTBOUND_CLOSE);
                    break;
                }
            }
        } else {
            vo.setStatus(OutboundConstant.STATUS_OUTBOUND_START);
        }
        vo.setCnum(headBO.getOutboundNum().add(BigDecimal.valueOf(null == ofBO ? 0 : ofBO.size())));

        session.setAttribute(CommonConstant.SESSION_OHID, dto.getOhid());

        return ResultVO.ok().setData(vo);
    }

    /**
     * 执行
     *
     * @param session
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO execute(HttpSession session) {
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if (null == imei) {
            return new ResultVO(1111);
        }

        Long ohid = (Long) session.getAttribute(CommonConstant.SESSION_OHID);
        if (null == ohid) {
            return new ResultVO(1002);
        }
        ForkliftPO curFPO = forkliftMapper.queryByImei(imei);

        String status;
        OutboundForkliftPO existOfPO = outboundForkliftMapper.queryByFid(curFPO.getForkliftId());
        if (null == existOfPO) {
            // 开始执行/辅助执行

            // 出库叉车信息
            OutboundForkliftPO ofPO = new OutboundForkliftPO();
            ofPO.setForkliftId(curFPO.getForkliftId());
            ofPO.setOutboundHeadId(ohid);
            outboundForkliftMapper.insert(ofPO);

            // 叉车状态 - 忙碌
            curFPO.setStatus(CommonConstant.STATUS_FORKLIFT_BUSY);
            forkliftMapper.updateByPrimaryKey(curFPO);

            // 叉车工作类型 - 出库
            session.setAttribute(CommonConstant.SESSION_STATUS_FORKLIFT, CommonConstant.FORKLIFT_WORK_OUTBOUND_START);

            status = OutboundConstant.STATUS_OUTBOUND_CLOSE;
        } else if (ohid.equals(existOfPO.getOutboundHeadId())) {
            // 关闭
            outboundForkliftMapper.deleteByFid(curFPO.getForkliftId());

            // 叉车状态 - 空闲
            curFPO.setStatus(CommonConstant.STATUS_FORKLIFT_IDLE);
            forkliftMapper.updateByPrimaryKey(curFPO);

            session.removeAttribute(CommonConstant.SESSION_STATUS_FORKLIFT);
            List<OutboundForkliftBO> allOFBO = outboundForkliftMapper.queryByOhid(ohid);

            status = allOFBO.size() == 0 ? OutboundConstant.STATUS_OUTBOUND_START : OutboundConstant.STATUS_OUTBOUND_ASSIST;
        } else {
            return new ResultVO<>(1010, "当前叉车已进行其他出库单任务！");
        }

        // websocket
        WebSocketServer.sendAllMsg(WebSocketVO.createShowVO(ohid, CommonConstant.FLAG_OUTBOUND));

        return ResultVO.ok().setData(status);
    }

}
