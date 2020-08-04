package com.smartindustry.authority.controller;

import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.dto.UserDTO;
import com.smartindustry.authority.service.IUserService;
import com.smartindustry.common.mapper.am.UserMapper;
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
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return userService.pageQuery(reqData);
    }

    @PostMapping("status")
    public ResultVO status(@RequestBody List<OperateDTO> dtos) {
        return userService.batchUpdate(dtos);
    }

    @PostMapping("batchDelete")
    public ResultVO batchDelete(@RequestBody List<OperateDTO> dtos) {
        return userService.batchDelete(dtos);
    }

    @PostMapping("insert")
    public ResultVO insert(@RequestBody UserDTO dto) {
        return userService.insert(dto);
    }

    @PostMapping("update")
    public ResultVO update(@RequestBody UserDTO dto) {
        return userService.update(dto);
    }


    @PostMapping("updatePassword")
    public ResultVO updatePassword(@RequestBody OperateDTO dto) {
        return userService.updatePassword(dto);
    }

    @PostMapping("chooseRole")
    public ResultVO queryRole(OperateDTO dto) {
        return userService.queryRole(dto);
    }





}
