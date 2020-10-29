package com.smartindustry.outbound.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.service.IFinishOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/10/29 9:24
 * @description: 成品出库
 * @version: 1.0
 */
@RestController
@RequestMapping("pda-outbound")
public class FinishOutboundController {
    @Autowired
    private IFinishOutboundService finishOutboundService;

    @PostMapping("erp")
    public ResultVO erp() {
        return finishOutboundService.erp();
    }
}
