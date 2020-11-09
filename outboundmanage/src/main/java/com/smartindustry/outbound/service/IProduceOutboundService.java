package com.smartindustry.outbound.service;

import com.smartindustry.common.vo.ResultVO;

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
}
