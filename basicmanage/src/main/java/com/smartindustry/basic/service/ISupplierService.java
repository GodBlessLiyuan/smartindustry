package com.smartindustry.basic.service;

import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.dto.SupplierDTO;
import com.smartindustry.common.vo.ResultVO;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:19
 * @description: 供应商管理
 * @version: 1.0
 */
public interface ISupplierService {
    ResultVO pageQuery(Map<String, Object> reqData);

    ResultVO edit(SupplierDTO dto);

    ResultVO delete(List<Long> sids);

    ResultVO detail(OperateDTO dto);

    ResultVO record(OperateDTO dto);

    ResultVO queryAll();
}
