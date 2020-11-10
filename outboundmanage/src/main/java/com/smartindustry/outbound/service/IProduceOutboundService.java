package com.smartindustry.outbound.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.dto.OperateDTO;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:26 2020/11/8
 * @version: 1.0.0
 * @description:
 */
public interface IProduceOutboundService {
    /**
     * 成品出库单的分页查询
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);

    /**
     * 根据叉车id或系统查询出库操作记录
     * @param dto
     * @return
     */
    ResultVO queryOutboundRecord(OperateDTO dto);

    /**
     * 成品出库查看详情
     * @param dto
     * @return
     */
    ResultVO detail(OperateDTO dto);
}
