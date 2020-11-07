package com.smartindustry.datasynchronize.service;

import com.smartindustry.common.vo.ResultVO;

import java.util.Map;

/**
 * @author hui.feng
 * @date created in 2020/11/5
 * @description
 */
public interface IPurchaseStorageService {
    ResultVO sync(Map<String, Object> reqData);
}
