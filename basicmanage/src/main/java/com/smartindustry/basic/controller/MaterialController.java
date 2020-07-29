package com.smartindustry.basic.controller;

import com.smartindustry.basic.service.IMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:18
 * @description: 物料管理
 * @version: 1.0
 */
@RequestMapping("material")
@RestController
public class MaterialController {
    @Autowired
    private IMaterialService materialService;
}
