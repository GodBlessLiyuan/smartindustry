package com.smartindustry.basic.controller;

import com.smartindustry.basic.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:17
 * @description: 供应商管理
 * @version: 1.0
 */
@RequestMapping("supplier")
@RestController
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;

}
