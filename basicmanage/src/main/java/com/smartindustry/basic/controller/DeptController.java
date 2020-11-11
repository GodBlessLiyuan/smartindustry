package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.DeptDTO;
import com.smartindustry.basic.service.IDeptService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PostMapping("pageQuery")
    @PreAuthorize("@ss.hasPermi('bm:am:dept:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return deptService.pageQuery(reqData);
    }

    @PostMapping("detail")
    public ResultVO detail(@RequestBody DeptDTO deptDTO){
        return deptService.detail(deptDTO.getDid());
    }

}
