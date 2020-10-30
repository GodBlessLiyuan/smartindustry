package com.smartindustry.pda.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.FinishOutboundDTO;
import com.smartindustry.pda.service.IFinishOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("online")
    public ResultVO online(@RequestBody FinishOutboundDTO dto) {
        return finishOutboundService.online(dto);
    }
}
