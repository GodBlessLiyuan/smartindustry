package com.smartindustry.pda.controller;

import com.smartindustry.common.vo.ResultVO;

import com.smartindustry.pda.dto.StoragePreDTO;
import com.smartindustry.pda.service.ISpareAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:12 2020/11/2
 * @version: 1.0.0
 * @description:  备料
 */
@RequestMapping("spare")
@RestController
public class SpareAreaController {
    @Autowired
    private ISpareAreaService spareAreaService;

    @PostMapping("enterSpare")
    public ResultVO enterSpare(@RequestBody StoragePreDTO dto, HttpSession session) {
        return spareAreaService.enterSpare(dto,session);
    }
}
