package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.PrintLabelDTO;
import com.smartindustry.storage.service.ILabelManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/6/28 15:05
 * @description: 标签管理
 * @version: 1.0
 */
@RequestMapping("label")
@RestController
public class LabelManageController {
    @Autowired
    private ILabelManageService labelManageService;

    @RequestMapping("query")
    public ResultVO query(@RequestParam(value = "rbId") Long rbId) {
        return labelManageService.query(rbId);
    }

    @RequestMapping("insert")
    public ResultVO insert(@RequestBody PrintLabelDTO dto) {
        return labelManageService.insert(dto);
    }

    @RequestMapping("finish")
    public ResultVO finish(@RequestParam(value = "rbId") Long rbId) {
        return labelManageService.finish(rbId);
    }
}
