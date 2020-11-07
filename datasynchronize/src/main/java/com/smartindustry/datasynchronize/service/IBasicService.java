package com.smartindustry.datasynchronize.service;

import com.smartindustry.common.vo.ResultVO;

/**
 * @author hui.feng
 * @date created in 2020/11/5
 * @description
 */
public interface IBasicService {
    ResultVO material();

    ResultVO client();

    ResultVO supplier();

    ResultVO dept();

    ResultVO role();

    ResultVO user();
}
