package com.smartindustry.pda.service.impl;

import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.mapper.om.OutboundBodyMapper;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.mapper.pda.OutboundForkliftMapper;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.common.pojo.pda.OutboundForkliftPO;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.util.DateUtil;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.constant.OutboundConstant;
import com.smartindustry.pda.dto.OutboundDTO;
import com.smartindustry.pda.service.IOutboundService;
import com.smartindustry.pda.socket.WebSocketServer;
import com.smartindustry.pda.util.OutboundNoUtil;
import com.smartindustry.pda.vo.OutboundDetailVO;
import com.smartindustry.pda.vo.OutboundListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        headPO.setStatus((byte) 3);
        headPO.setCreateTime(new Date());
        headPO.setDr((byte) 1);
        outboundHeadMapper.insert(headPO);

        // 出库表体
        OutboundBodyPO bodyPO1 = new OutboundBodyPO();
        bodyPO1.setOutboundHeadId(headPO.getOutboundHeadId());
        bodyPO1.setMaterialId(1L);
        bodyPO1.setOutingNum(new BigDecimal(6));
        bodyPO1.setCreateTime(new Date());
        bodyPO1.setDr((byte) 1);
        outboundBodyMapper.insert(bodyPO1);

        OutboundBodyPO bodyPO2 = new OutboundBodyPO();
        bodyPO2.setOutboundHeadId(headPO.getOutboundHeadId());
        bodyPO2.setMaterialId(2L);
        bodyPO2.setOutingNum(new BigDecimal(12));
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

        return ResultVO.ok().setData(forkliftPO.getForkliftNo());
    }

    /**
     * 列表区域
     *
     * @param session
     * @param dto
     * @return
     */
    @Override
    public ResultVO list(HttpSession session, OutboundDTO dto) {
        if (null == dto.getType()) {
            return new ResultVO(1001);
        }

        // 叉车信息
        ForkliftPO forkliftPO = forkliftMapper.queryByImei((String) session.getAttribute("imei"));
        if (null == forkliftPO) {
            return new ResultVO(1002);
        }

        List<OutboundHeadBO> headBOs = outboundHeadMapper.queryPdaByType(dto.getType());

        // session 存储
        Set<Long> ohids = new HashSet<>();
        for (OutboundHeadBO headBO : headBOs) {
            ohids.add(headBO.getOutboundHeadId());
        }
        session.setAttribute(OutboundConstant.SESSION_OHIDS, ohids);

        return ResultVO.ok().setData(OutboundListVO.convert(headBOs));
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

        // 出库信息
        OutboundHeadBO headBO = outboundHeadMapper.queryByOhid(dto.getOhid());
        if (null == headBO) {
            return new ResultVO(1002);
        }
        OutboundDetailVO vo = OutboundDetailVO.convert(headBO);

        // 叉车信息
        List<ForkliftPO> pos = forkliftMapper.queryByOhid(dto.getOhid());
        if (null != pos && pos.size() > 0) {
            String imei = (String) session.getAttribute(OutboundConstant.SESSION_IMEI);
            vo.setStatus("辅助执行");

            List<String> fnos = new ArrayList<>(pos.size());
            for (ForkliftPO po : pos) {
                fnos.add(po.getForkliftNo());
                if (imei.equals(po.getImeiNo())) {
                    vo.setStatus("关闭");
                }
            }
            vo.setFnos(fnos);
        } else {
            vo.setStatus("开始执行");
        }

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
        Long ohid = (Long) session.getAttribute(OutboundConstant.SESSION_OHID);
        ForkliftPO fPO = forkliftMapper.queryByImei(imei);

        List<ForkliftPO> pos = forkliftMapper.queryByOhid(ohid);
        if (null == pos || pos.size() == 0) {
            // 开始任务
            OutboundForkliftPO ofPO = new OutboundForkliftPO();
            ofPO.setForkliftId(fPO.getForkliftId());
            ofPO.setOutboundHeadId(ohid);
            outboundForkliftMapper.insert(ofPO);

            session.setAttribute(OutboundConstant.SESSION_OHID, ohid);

            return ResultVO.ok();
        }

        for (ForkliftPO po : pos) {
            if (imei.equals(po.getImeiNo())) {
                // 关闭
                outboundForkliftMapper.deleteByFid(po.getForkliftId());
                session.removeAttribute(OutboundConstant.SESSION_OHID);
                return ResultVO.ok();
            }
        }

        // 辅助任务
        OutboundForkliftPO ofPO = new OutboundForkliftPO();
        ofPO.setForkliftId(fPO.getForkliftId());
        ofPO.setOutboundHeadId(ohid);
        outboundForkliftMapper.insert(ofPO);

        session.setAttribute(OutboundConstant.SESSION_OHID, ohid);

        return ResultVO.ok();
    }

    /**
     * 出库
     *
     * @param session
     * @param dto
     * @return
     */
    @Override
    public ResultVO outbound(HttpSession session, OutboundDTO dto) {
        if (null == dto.getMrfid() && null == dto.getLrfid()) {
            return new ResultVO(1001);
        }

        // 当前叉车信息
        String imei = (String) session.getAttribute(OutboundConstant.SESSION_IMEI);
        ForkliftPO fPO = forkliftMapper.queryByImei(imei);
        OutboundForkliftPO ofPO = outboundForkliftMapper.queryByFid(fPO.getForkliftId());
        if (null == ofPO) {
            // 尚未开始任务
            WebSocketServer.sendMsg(imei, "请先开始任务！");
            return new ResultVO(1003);
        }

        if (null == dto.getLrfid()) {
            // 叉起砧板

        } else {
            // 砧板入库
        }

        return ResultVO.ok();
    }
}
