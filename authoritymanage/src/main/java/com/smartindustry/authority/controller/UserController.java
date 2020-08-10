package com.smartindustry.authority.controller;

import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.dto.EditDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.dto.UserDTO;
import com.smartindustry.authority.service.IUserService;
import com.smartindustry.common.mapper.am.UserMapper;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:39 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@RequestMapping("user")
@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("pageQuery")
    @PreAuthorize("@ss.hasPermi('am:user:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return userService.pageQuery(reqData);
    }

    @PostMapping("status")
    @PreAuthorize("@ss.hasPermi('am:user:disable')")
    public ResultVO status(@RequestBody List<OperateDTO> dtos) {
        return userService.batchUpdate(dtos);
    }

    @PreAuthorize("@ss.hasPermi('am:user:delete')")
    @PostMapping("delete")
    public ResultVO delete(@RequestBody List<Long> uids) {
        return userService.delete(uids);
    }

    @PreAuthorize("@ss.hasPermi('am:user:insert')")
    @PostMapping("insert")
    public ResultVO insert(@RequestBody UserDTO dto) {
        return userService.insert(dto);
    }

    @PostMapping("update")
    @PreAuthorize("@ss.hasPermi('am:user:update')")
    public ResultVO update(@RequestBody UserDTO dto) {
        return userService.update(dto);
    }

    @PostMapping("updatePassword")
    @PreAuthorize("@ss.hasPermi('am:user:password')")
    public ResultVO updatePassword(@RequestBody OperateDTO dto) {
        return userService.updatePassword(dto);
    }

    @PostMapping("chooseRole")
    public ResultVO queryRole() {
        return userService.queryRole();
    }

    @PostMapping("queryHavePerms")
    public ResultVO queryHavePerms(@RequestBody OperateDTO dto) {
        return userService.queryHavePerms(dto);
    }

    @PostMapping("queryUserRecord")
    public ResultVO queryUserRecord(@RequestBody Map<String, Object> reqData) {
        return userService.queryUserRecord(reqData);
    }

    @PostMapping("editPassword")
    @PreAuthorize("@ss.hasPermi('am:userinfo:password')")
    public ResultVO editPassword(HttpServletRequest session,@RequestBody EditDTO dto) {
        return userService.editPassword(session,dto);
    }

    @PostMapping("queryUserMsg")
    public ResultVO queryUserMsg(HttpServletRequest session){
        return userService.queryUserMsg(session);
    }

    @PostMapping("updateUser")
    @PreAuthorize("@ss.hasPermi('am:userinfo:update')")
    public ResultVO updateUser(HttpServletRequest session,@RequestBody UserDTO dto) {
        return userService.updateUser(session,dto);
    }
}
