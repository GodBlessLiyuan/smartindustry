package com.smartindustry.authority.controller;

import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.service.IDeptService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:21 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@RequestMapping("dept")
@RestController
public class DeptController {
    @Autowired
    private IDeptService deptService;

    /**
     * 对部门信息进行分页模糊查询
     * @param reqData
     * @return
     */
    @PostMapping("deptQuery")
    public ResultVO deptQuery(@RequestBody Map<String, Object> reqData) {
        return deptService.deptQuery(reqData);
    }
}
