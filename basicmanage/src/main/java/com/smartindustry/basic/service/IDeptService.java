package com.smartindustry.basic.service;

import com.smartindustry.common.vo.ResultVO;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:23 2020/7/30
 * @version: 1.0.0
 * @description:
 */
public interface IDeptService {

    /**
     * 分页模糊查询部门信息
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);

    ResultVO detail(Long did);
}
