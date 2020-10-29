package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:14 2020/10/29
 * @version: 1.0.0
 * @description:
 */
public interface IForkLiftService {
    /**
     * 叉车基础信息管理的分页查询
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);
}
