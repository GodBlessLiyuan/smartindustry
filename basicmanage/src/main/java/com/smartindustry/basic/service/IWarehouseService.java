package com.smartindustry.basic.service;

import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.dto.WarehouseDTO;
import com.smartindustry.common.vo.ResultVO;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:19
 * @description: 仓库管理
 * @version: 1.0
 */
public interface IWarehouseService {
    ResultVO pageQuery(Map<String, Object> reqData);

    ResultVO typeQuery();

    ResultVO edit(WarehouseDTO dto);

    ResultVO delete(List<Long> wids);

    ResultVO detail(OperateDTO dto);

    ResultVO record(OperateDTO dto);
}
