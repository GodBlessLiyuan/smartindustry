package com.smartindustry.authority.controller;

import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.service.IDeptService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return deptService.pageQuery(reqData);
    }

    @PostMapping("status")
    public ResultVO batchUpdate(@RequestBody List<OperateDTO> dtos) {
        return deptService.batchUpdate(dtos);
    }

    @PostMapping("insert")
    public ResultVO insert(@RequestBody DeptDTO dto) {
        return deptService.insert(dto);
    }

    @PostMapping("update")
    public ResultVO update(@RequestBody DeptDTO dto) {
        return deptService.update(dto);
    }

    @PostMapping("batchDelete")
    public ResultVO batchDelete(@RequestBody List<OperateDTO> dtos) {
        return deptService.batchDelete(dtos);
    }

    @PostMapping("queryDeptName")
    public ResultVO queryDeptName() {
        return deptService.queryDeptName();
    }

    @PostMapping("chooseLeader")
    public ResultVO queryLeader(OperateDTO dto) {
        return deptService.queryLeader(dto);
    }
}
