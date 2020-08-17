package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LogisticsDTO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.ReceiptDTO;
import com.smartindustry.storage.service.IReceiptManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 10:44
 * @description: 收料管理
 * @version: 1.0
 */
@RequestMapping("receipt")
@RestController
public class ReceiptManageController {
    @Autowired
    private IReceiptManageService receiptManageService;

    @PostMapping("pageQuery")
    @PreAuthorize("@ss.hasAnyPermi('sm:rm:porc:query,sm:rm:src:query,sm:rm:prm:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return receiptManageService.pageQuery(reqData);
    }

    @PostMapping("insert")
    @PreAuthorize("@ss.hasAnyPermi('sm:rm:porc:insert,sm:rm:src:insert,sm:rm:prm:insert')")
    public ResultVO insert(@RequestBody ReceiptDTO dto) {
        return receiptManageService.insert(dto);
    }

    @PostMapping("delete")
    @PreAuthorize("@ss.hasAnyPermi('sm:rm:porc:delete,sm:rm:src:delete,sm:rm:prm:delete')")
    public ResultVO delete(@RequestBody List<Long> rbIds) {
        return receiptManageService.delete(rbIds);
    }

    @PostMapping("editLog")
    public ResultVO editLog(@RequestBody LogisticsDTO dto) {
        return receiptManageService.editLog(dto);
    }

    @PostMapping("record")
    public ResultVO record(@RequestBody OperateDTO dto) {
        return receiptManageService.record(dto);
    }

    @PostMapping("materialQuery")
    public ResultVO materialQuery(@RequestBody Map<String, Object> reqData) {
        return receiptManageService.materialQuery(reqData);
    }
}
