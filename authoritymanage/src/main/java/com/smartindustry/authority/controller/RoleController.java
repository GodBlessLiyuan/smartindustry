package com.smartindustry.authority.controller;

import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.dto.RoleDTO;
import com.smartindustry.authority.service.IRoleService;
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
 * @date: Created in 15:11 2020/7/31
 * @version: 1.0.0
 * @description:
 */
@RequestMapping("role")
@RestController
public class RoleController {
    @Autowired
    IRoleService roleService;

    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return roleService.pageQuery(reqData);
    }

    @PostMapping("status")
    public ResultVO batchUpdate(@RequestBody List<OperateDTO> dtos) {
        return roleService.batchUpdate(dtos);
    }

    @PostMapping("insert")
    public ResultVO insert(@RequestBody RoleDTO dto) {
        return roleService.insert(dto);
    }

    @PostMapping("update")
    public ResultVO update(@RequestBody RoleDTO dto) {
        return roleService.update(dto);
    }

    @PostMapping("batchDelete")
    public ResultVO batchDelete(@RequestBody List<OperateDTO> dtos) {
        return roleService.batchDelete(dtos);
    }
}
