package com.smartindustry.outbound.controller;

import com.smartindustry.outbound.service.IMaterialOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 19:00
 * @description: 物料出库
 * @version: 1.0
 */
@RequestMapping("outbound")
@RestController
public class MaterialOutboundController {
    @Autowired
    private IMaterialOutboundService materialOutboundService;
}
