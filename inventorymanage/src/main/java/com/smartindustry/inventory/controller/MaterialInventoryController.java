package com.smartindustry.inventory.controller;

import com.smartindustry.inventory.service.IMaterialInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 16:02
 * @description: 物料库存统计
 * @version: 1.0
 */
@RequestMapping("material")
@RestController
public class MaterialInventoryController {
    @Autowired
    private IMaterialInventoryService materialInventoryService;
}
