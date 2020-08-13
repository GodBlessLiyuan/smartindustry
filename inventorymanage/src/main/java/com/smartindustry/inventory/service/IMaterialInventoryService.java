package com.smartindustry.inventory.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.dto.SafeStockDTO;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 16:05
 * @description: 物料库存统计
 * @version: 1.0
 */
public interface IMaterialInventoryService {
    ResultVO pageQuery(Map<String, Object> reqData);

    ResultVO safeStock(SafeStockDTO dto);
}
