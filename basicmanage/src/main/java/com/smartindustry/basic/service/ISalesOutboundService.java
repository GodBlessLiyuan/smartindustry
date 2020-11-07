package com.smartindustry.basic.service;

import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.common.vo.ResultVO;

import java.util.Map;

/**
 * @author hui.feng
 * @date created in 2020/11/5
 * @description
 */
public interface ISalesOutboundService {
    ResultVO pageQuery(Map<String, Object> reqData);

    ResultVO detail(OperateDTO dto);
}
