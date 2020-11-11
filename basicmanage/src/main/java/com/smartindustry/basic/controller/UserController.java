package com.smartindustry.basic.controller;

import com.smartindustry.basic.service.IUserService;
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
    @PreAuthorize("@ss.hasPermi('bm:am:user:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return userService.pageQuery(reqData);
    }

}
