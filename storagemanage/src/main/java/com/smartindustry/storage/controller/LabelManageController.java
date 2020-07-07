package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LabelSplitDTO;
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
    public ResultVO query(@RequestParam(value = "rbid") Long rbId) {
        return labelManageService.query(rbId);
    }

    @RequestMapping("queryPid")
    public ResultVO queryPid(@RequestParam(value = "rbid") Long rbId, @RequestParam(value = "pid") String pid) {
        return labelManageService.queryPid(rbId, pid);
    }

    @RequestMapping("reprint")
    public ResultVO reprint(@RequestParam(value = "plid") Long plId, @RequestParam(value = "num") Integer num) {
        return labelManageService.reprint(plId, num);
    }

    @RequestMapping("insert")
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

    @RequestMapping("split")
    public ResultVO split(@RequestBody LabelSplitDTO dto) {
        return labelManageService.split(dto);
    }
}
