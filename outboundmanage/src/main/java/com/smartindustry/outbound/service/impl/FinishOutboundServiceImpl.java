package com.smartindustry.outbound.service.impl;

import com.smartindustry.common.mapper.om.OutboundBodyMapper;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.common.util.DateUtil;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.service.IFinishOutboundService;
import com.smartindustry.outbound.util.OutboundNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

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
}
