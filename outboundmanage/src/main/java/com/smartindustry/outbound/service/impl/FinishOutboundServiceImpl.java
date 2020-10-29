package com.smartindustry.outbound.service.impl;

import com.smartindustry.common.mapper.om.OutboundBodyMapper;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.service.IFinishOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
    }
}
