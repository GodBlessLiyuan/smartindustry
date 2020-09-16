package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LabelSplitDTO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.PrintLabelDTO;
import com.smartindustry.storage.service.ILabelManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * 根据收料ID 获取物料标签列表 (未入库的)
     * @param dto
     * @return
     */
    @PostMapping("queryRbid")
    public ResultVO queryRbid(@RequestBody OperateDTO dto) {
        return labelManageService.queryRbid(dto);
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
    @PreAuthorize("@ss.hasAnyPermi('sm:rm:porc:entrylabel,sm:rm:src:entrylabel,sm:rm:prm:entrylabel')")
    public ResultVO insert(@RequestBody PrintLabelDTO dto) {
        return labelManageService.insert(dto);
    }

    @PostMapping("delete")
    public ResultVO delete(@RequestBody OperateDTO dto) {
        return labelManageService.delete(dto);
    }

    @PostMapping("finish")
    @PreAuthorize("@ss.hasAnyPermi('sm:rm:porc:entrylabel,sm:rm:src:entrylabel,sm:rm:prm:entrylabel')")
    public ResultVO finish(@RequestBody OperateDTO dto) {
        return labelManageService.finish(dto);
    }

    @PostMapping("split")
    @PreAuthorize("@ss.hasAnyPermi('sm:qm:iqctest:entrylabel,sm:qm:iqcqe:printlabel,sm:qm:qetest:printlabel')")
    public ResultVO split(@RequestBody LabelSplitDTO dto) {
        return labelManageService.split(dto);
    }
}
