package com.smartindustry.inventory.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.dto.SafeStockDTO;

import java.util.Map;

/**
 * @author: jiangchaojie
 * @date: Created in 2020/8/10 16:05
 * @description: 物料库存统计
 * @version: 1.0
 */
public interface IMaterialInventoryService {
    /**
     * 物料库存统计分页查询
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);

    /**
     * 设置安全库存上下限
     * @param dto
     * @return
     */
    ResultVO safeStock(SafeStockDTO dto);

    /**
     * 成品库存明细分页查询
     * @param reqData
     * @return
     */
    ResultVO pageQueryPro(Map<String, Object> reqData);
}
