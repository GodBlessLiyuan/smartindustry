package com.smartindustry.basic.service;

import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.common.vo.ResultVO;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:19
 * @description: 供应商管理
 * @version: 1.0
 */
public interface ISupplierService {
    ResultVO pageQuery(Map<String, Object> reqData);

    ResultVO detail(OperateDTO dto);

}
