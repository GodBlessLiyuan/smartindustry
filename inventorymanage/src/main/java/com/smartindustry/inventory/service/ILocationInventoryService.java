package com.smartindustry.inventory.service;

import com.smartindustry.common.vo.ResultVO;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 16:04
 * @description: 货位库存统计
 * @version: 1.0
 */
public interface ILocationInventoryService {
    ResultVO pageQuery(Map<String, Object> reqData);
}
