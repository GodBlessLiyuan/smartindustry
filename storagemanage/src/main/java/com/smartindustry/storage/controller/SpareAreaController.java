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

    // 生产入库单物料进备料区
    @PostMapping("enterSpare")
    public ResultVO enterSpare(@RequestBody StoragePreDTO dto) {
        return spareAreaService.enterSpare(dto);
    }
}
