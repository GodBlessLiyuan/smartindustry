package com.smartindustry.basic.controller.handler;

import com.smartindustry.common.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 10:56
 * @description: 异常处理
 * @version: 1.0
 */
@RestControllerAdvice
public class BasicExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(BasicExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResultVO handlerPromptException(Exception e) {
        logger.error("BasicExceptionHandler: ", e);
        return new ResultVO(2000);
    }
}
