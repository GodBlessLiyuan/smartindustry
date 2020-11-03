package com.smartindustry.pda.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.dto.OutboundDTO;
import com.smartindustry.pda.service.IOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author: xiahui
 * @date: Created in 2020/10/29 9:24
 * @description: 成品出库
 * @version: 1.0
 */
@RestController
@RequestMapping("outbound")
public class OutboundController {
    @Autowired
    private IOutboundService finishOutboundService;

    @PostMapping("erp")
    public ResultVO erp() {
        return finishOutboundService.erp();
    }

    @PostMapping("online")
    public ResultVO online(HttpSession session, @RequestBody OutboundDTO dto) {
        return finishOutboundService.online(session, dto);
    }

    @PostMapping("list")
    public ResultVO list(HttpSession session, @RequestBody OutboundDTO dto) {
        return finishOutboundService.list(session, dto.getType());
    }

    @PostMapping("detail")
    public ResultVO detail(HttpSession session, @RequestBody OutboundDTO dto) {
        return finishOutboundService.detail(session, dto);
    }

    @PostMapping("execute")
    public ResultVO execute(HttpSession session) {
        return finishOutboundService.execute(session);
    }

    @PostMapping("outbound")
    public ResultVO outbound(HttpSession session, @RequestBody OutboundDTO dto) {
        return finishOutboundService.outbound(session, dto);
    }
}
