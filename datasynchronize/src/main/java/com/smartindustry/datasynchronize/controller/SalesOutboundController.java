package com.smartindustry.datasynchronize.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.datasynchronize.service.ISalesOutboundService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hui.feng
 * @date created in 2020/11/5
 * @description
 */
@RestController
@RequestMapping("sales")
public class SalesOutboundController {

    @Resource
    private ISalesOutboundService salesOutboundService;

    @PostMapping("sync")
    public ResultVO sync() {
        return salesOutboundService.sync();
    }
}
