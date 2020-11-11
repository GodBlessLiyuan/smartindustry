package com.smartindustry.basic.service;

import com.smartindustry.common.vo.ResultVO;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:53 2020/7/30
 * @version: 1.0.0
 * @description:
 */
public interface IUserService {
    /**
     * 用户的分页模糊查询
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);
}
