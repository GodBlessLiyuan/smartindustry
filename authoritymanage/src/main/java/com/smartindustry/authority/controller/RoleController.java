package com.smartindustry.authority.controller;

import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.dto.RoleDTO;
import com.smartindustry.authority.service.IRoleService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@ss.hasPermi('am:role:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return roleService.pageQuery(reqData);
    }

    @PostMapping("status")
    @PreAuthorize("@ss.hasPermi('am:role:disable')")
    public ResultVO status(@RequestBody List<OperateDTO> dtos) {
        return roleService.batchUpdate(dtos);
    }

    @PreAuthorize("@ss.hasPermi('am:role:insert')")
    @PostMapping("insert")
    public ResultVO insert(@RequestBody RoleDTO dto) {
        return roleService.insert(dto);
    }

    @PostMapping("update")
    @PreAuthorize("@ss.hasPermi('am:role:update')")
    public ResultVO update(@RequestBody RoleDTO dto) {
        return roleService.update(dto);
    }

    @PreAuthorize("@ss.hasPermi('am:role:delete')")
    @PostMapping("delete")
    public ResultVO delete(@RequestBody List<Long> dtos) {
        return roleService.delete(dtos);
    }

    @PostMapping("queryAllMenu")
    public ResultVO queryAllMenu() {
        return roleService.queryAllMenu();
    }

    @PostMapping("queryAllButton")
    public ResultVO queryAllButton() {
        return roleService.queryAllButton();
    }

    @PostMapping("updatePerms")
    public ResultVO updatePerms(@RequestBody OperateDTO dto) {
        return roleService.updatePerms(dto);
    }

    @PostMapping("queryRolePerms")
    public ResultVO queryRolePerms(@RequestBody OperateDTO dto) {
        return roleService.queryRolePerms(dto);
    }

    @PostMapping("queryRoleRecord")
    public ResultVO queryRoleRecord(@RequestBody Map<String, Object> reqData) {
        return roleService.queryRoleRecord(reqData);
    }

}
