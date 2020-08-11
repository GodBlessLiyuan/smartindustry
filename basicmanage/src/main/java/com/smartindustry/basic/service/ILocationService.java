package com.smartindustry.basic.service;

import com.smartindustry.basic.dto.LocationDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.common.vo.ResultVO;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:18
 * @description: 货位管理
 * @version: 1.0
 */
public interface ILocationService {
    ResultVO pageQuery(Map<String, Object> reqData);

    ResultVO edit(LocationDTO dto);

    ResultVO delete(List<Long> lids);

    ResultVO detail(OperateDTO dto);

    ResultVO record(OperateDTO dto);

    ResultVO queryAll();
}
