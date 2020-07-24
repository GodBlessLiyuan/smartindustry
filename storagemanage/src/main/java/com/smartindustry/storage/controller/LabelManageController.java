package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LabelSplitDTO;
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
    public ResultVO query(@RequestParam(value = "rbid") Long rbId, @RequestParam(value = "status", defaultValue = "false") Boolean status) {
        return labelManageService.query(rbId, status);
    }

    @RequestMapping("queryPid")
    public ResultVO queryPid(@RequestParam(value = "rbid") Long rbId, @RequestParam(value = "pid") String pid) {
        return labelManageService.queryPid(rbId, pid);
    }

    @RequestMapping("print")
    public ResultVO print(@RequestParam(value = "pid") String pid, @RequestParam("status") Byte status) {
        return labelManageService.print(pid, status);
    }

    @RequestMapping("reprint")
    public ResultVO reprint(@RequestParam(value = "plid") Long plId, @RequestParam(value = "num") Integer num) {
        return labelManageService.reprint(plId, num);
    }

    @PostMapping("insert")
    public ResultVO insert(@RequestBody PrintLabelDTO dto) {
        return labelManageService.insert(dto);
    }

    @RequestMapping("delete")
    public ResultVO delete(@RequestParam(value = "rbid") Long rbId, @RequestParam(value = "plid") Long plId) {
        return labelManageService.delete(rbId, plId);
    }

    @RequestMapping("finish")
    public ResultVO finish(@RequestParam(value = "rbid") Long rbId) {
        return labelManageService.finish(rbId);
    }

    @PostMapping("split")
    public ResultVO split(@RequestBody LabelSplitDTO dto) {
        return labelManageService.split(dto);
    }
}
