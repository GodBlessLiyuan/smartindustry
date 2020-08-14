package com.smartindustry.authority.controller;

import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.dto.RoleDTO;
import com.smartindustry.authority.service.IRoleService;
import com.smartindustry.common.mapper.am.AuthorityMapper;
import com.smartindustry.common.mapper.am.MUserAuthorityMapper;
import com.smartindustry.common.pojo.am.AuthorityPO;
import com.smartindustry.common.pojo.am.MUserAuthorityPO;
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
    private IRoleService roleService;

    @PostMapping("pageQuery")
    @PreAuthorize("@ss.hasPermi('bm:am:role:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return roleService.pageQuery(reqData);
    }

    @PostMapping("status")
    @PreAuthorize("@ss.hasAnyPermi('bm:am:role:disable,bm:am:role:enable')")
    public ResultVO status(@RequestBody List<OperateDTO> dtos) {
        return roleService.batchUpdate(dtos);
    }

    @PostMapping("insert")
    @PreAuthorize("@ss.hasPermi('bm:am:role:insert')")
    public ResultVO insert(@RequestBody RoleDTO dto) {
        return roleService.insert(dto);
    }

    @PostMapping("update")
    @PreAuthorize("@ss.hasPermi('bm:am:role:update')")
    public ResultVO update(@RequestBody RoleDTO dto) {
        return roleService.update(dto);
    }

    @PostMapping("delete")
    @PreAuthorize("@ss.hasPermi('bm:am:role:delete')")
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
    @PreAuthorize("@ss.hasPermi('bm:am:role:site')")
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
