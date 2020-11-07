package com.smartindustry.datasynchronize.service;

import com.smartindustry.common.vo.ResultVO;

import java.util.Map;

/**
 * @author hui.feng
 * @date created in 2020/11/5
 * @description
 */
public interface IBasicService {
    ResultVO material(Map<String, Object> reqData);

    ResultVO client(Map<String, Object> reqData);

    ResultVO supplier(Map<String, Object> reqData);

    ResultVO dept(Map<String, Object> reqData);

    ResultVO role(Map<String, Object> reqData);

    ResultVO user(Map<String, Object> reqData);
}
