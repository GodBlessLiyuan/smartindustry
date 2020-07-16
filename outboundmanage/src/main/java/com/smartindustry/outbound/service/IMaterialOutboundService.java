package com.smartindustry.outbound.service;

import com.smartindustry.common.vo.ResultVO;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 19:01
 * @description: 物料出库
 * @version: 1.0
 */
public interface IMaterialOutboundService {
    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param reqData
     * @return
     */
    ResultVO pageQuery(int pageNum, int pageSize, Map<String, Object> reqData);
}
