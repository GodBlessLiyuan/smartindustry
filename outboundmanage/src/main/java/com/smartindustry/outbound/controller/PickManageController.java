package com.smartindustry.outbound.controller;

import com.smartindustry.outbound.service.IPickManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 18:58
 * @description: 拣货单管理
 * @version: 1.0
 */
@RequestMapping("pick")
@RestController
public class PickManageController {
    @Autowired
    private IPickManageService pickManageService;
}
