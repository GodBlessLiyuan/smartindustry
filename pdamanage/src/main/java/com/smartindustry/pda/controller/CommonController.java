package com.smartindustry.pda.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.dto.OutboundDTO;
import com.smartindustry.pda.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author: xiahui
 * @date: Created in 2020/11/4 18:29
 * @description: 公共模块
 * @version: 1.0
 */
@RestController
@RequestMapping("common")
public class CommonController {
    @Autowired
    private ICommonService commonService;

    @PostMapping("online")
    public ResultVO online(HttpSession session, @RequestBody OutboundDTO dto) {
        return commonService.online(session, dto);
    }



    @PostMapping("list")
    public ResultVO list(HttpSession session, @RequestBody OutboundDTO dto) {
        return commonService.list(session, dto.getType());
    }
}
