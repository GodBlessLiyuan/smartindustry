package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LabelSplitDTO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.PrintLabelDTO;
import com.smartindustry.storage.service.ILabelManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResultVO query(@RequestBody OperateDTO dto) {
        return labelManageService.query(dto);
    }

    @PostMapping("queryPid")
    public ResultVO queryPid(@RequestBody OperateDTO dto) {
        return labelManageService.queryPid(dto);
    }

    @PostMapping("print")
    public ResultVO print(@RequestBody OperateDTO dto) {
        return labelManageService.print(dto);
    }

    @PostMapping("reprint")
    public ResultVO reprint(@RequestBody OperateDTO dto) {
        return labelManageService.reprint(dto);
    }

    @PostMapping("insert")
    public ResultVO insert(@RequestBody PrintLabelDTO dto) {
        return labelManageService.insert(dto);
    }

    @PostMapping("delete")
    public ResultVO delete(@RequestBody OperateDTO dto) {
        return labelManageService.delete(dto);
    }

    @PostMapping("finish")
    public ResultVO finish(@RequestBody OperateDTO dto) {
        return labelManageService.finish(dto);
    }

    @PostMapping("split")
    public ResultVO split(@RequestBody LabelSplitDTO dto) {
        return labelManageService.split(dto);
    }
}
