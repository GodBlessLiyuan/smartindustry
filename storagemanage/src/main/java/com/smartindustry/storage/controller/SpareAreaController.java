package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.StoragePreDTO;
import com.smartindustry.storage.service.ISpareAreaService;
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

    @PostMapping("chooseMaterial")
    public ResultVO chooseMaterial(@RequestBody StoragePreDTO dto) {
        return spareAreaService.chooseMaterial(dto);
    }

    @PostMapping("showSpare")
    public ResultVO showSpare(@RequestBody StoragePreDTO dto) {
        return spareAreaService.showSpare(dto);
    }
}
