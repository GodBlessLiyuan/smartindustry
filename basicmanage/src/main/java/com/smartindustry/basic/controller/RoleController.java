package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IRoleService;
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

    /***
     * 查询所有按钮权限列表
     * @return
     */
    @PostMapping("queryAuthority")
    public ResultVO queryAuthority() {
        return roleService.queryAuthority();
    }

    /***
     * 更新角色权限
     * @param dto
     * @return
     */
    @PostMapping("updatePerms")
    @PreAuthorize("@ss.hasPermi('bm:am:role:site')")
    public ResultVO updatePerms(@RequestBody OperateDTO dto) {
        return roleService.updatePerms(dto);
    }

    /****
     * 根据角色id查找权限id
     * @param dto
     * @return
     */
    @PostMapping("queryRolePerms")
    public ResultVO queryRolePerms(@RequestBody OperateDTO dto) {
        return roleService.queryRolePerms(dto);
    }

    @PostMapping("queryRoleRecord")
    public ResultVO queryRoleRecord(@RequestBody Map<String, Object> reqData) {
        return roleService.queryRoleRecord(reqData);
    }
}
