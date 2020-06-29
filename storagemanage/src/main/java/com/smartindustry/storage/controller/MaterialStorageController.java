package com.smartindustry.storage.controller;

import com.smartindustry.storage.service.IMaterialStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 11:40
 * @description: 物料入库
 * @version: 1.0
 */
@RequestMapping("storage")
@RestController
public class MaterialStorageController {
    @Autowired
    private IMaterialStorageService materialStorageService;
}
