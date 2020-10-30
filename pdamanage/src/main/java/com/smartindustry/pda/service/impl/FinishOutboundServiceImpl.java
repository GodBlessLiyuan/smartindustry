package com.smartindustry.pda.service.impl;

import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.mapper.om.OutboundBodyMapper;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.util.DateUtil;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.dto.FinishOutboundDTO;
import com.smartindustry.pda.service.IFinishOutboundService;
import com.smartindustry.pda.util.OutboundNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/10/29 9:28
 * @description: 成品出库
 * @version: 1.0
 */
@Service
public class FinishOutboundServiceImpl implements IFinishOutboundService {
    @Autowired
    private OutboundHeadMapper outboundHeadMapper;
    @Autowired
    private OutboundBodyMapper outboundBodyMapper;
    @Autowired
    private ForkliftMapper forkliftMapper;

    /**
     * ERP 生成 出库单
     *
     * @return
     */
    @Override
    public ResultVO erp() {
        OutboundHeadPO headPO = new OutboundHeadPO();
        headPO.setOutboundNo(OutboundNoUtil.genOutboundHeadNo(outboundHeadMapper, OutboundNoUtil.OUTBOUND_HEAD_XS, new Date()));
        headPO.setSourceNo("XS" + DateUtil.date2Str(new Date(), DateUtil.YMDHMS));
        headPO.setSourceType((byte) 3);
        headPO.setStatus((byte) 3);
        headPO.setCreateTime(new Date());
        headPO.setDr((byte) 1);
        outboundHeadMapper.insert(headPO);

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
    public ResultVO online(HttpSession session, FinishOutboundDTO dto) {
        if (StringUtils.isEmpty(dto.getImei())) {
            return new ResultVO(1001);
        }

        ForkliftPO forkliftPO = forkliftMapper.queryByImei(dto.getImei());
        if (null == forkliftPO) {
            return new ResultVO(1002);
        }

        session.setAttribute("imei", dto.getImei());

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
    public ResultVO list(HttpSession session, FinishOutboundDTO dto) {
        if (null == dto.getType()) {
            return new ResultVO(1001);
        }

//        List<OutboundHeadBO> bos = outboundHeadMapper.queryByPdaStatus(dto.getType());

        ForkliftPO forkliftPO = forkliftMapper.queryByImei((String) session.getAttribute("imei"));
        if (null == forkliftPO) {
            return new ResultVO(1002);
        }




        return null;
    }
}
