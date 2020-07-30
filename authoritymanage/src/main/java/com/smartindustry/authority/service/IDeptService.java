package com.smartindustry.authority.service;

import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestBody;

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
    ResultVO deptQuery(Map<String, Object> reqData);
}
