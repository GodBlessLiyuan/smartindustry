package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LogisticsDTO;
import com.smartindustry.storage.dto.ReceiptDTO;
import com.smartindustry.storage.service.IReceiptManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("pageQuery")
    public ResultVO pageQuery(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                              @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                              @RequestParam(value = "supplier", required = false, defaultValue = "") String supplier,
                              @RequestParam(value = "status", required = false, defaultValue = "0") Byte status,
                              @RequestParam(value = "type") Byte type) {
        Map<String, Object> reqData = new HashMap<>(4);
        reqData.put("keyword", keyword);
        reqData.put("supplier", supplier);
        reqData.put("status", status);
        reqData.put("type", type);

        return receiptManageService.pageQuery(pageNum, pageSize, reqData);
    }

    @RequestMapping("insert")
    public ResultVO insert(@RequestBody ReceiptDTO dto) {
        return receiptManageService.insert(dto);
    }

    @RequestMapping("delete")
    public ResultVO delete(@RequestParam(value = "rbIds[]") List<Long> rbIds) {
        return receiptManageService.delete(rbIds);
    }

    @RequestMapping("editLog")
    public ResultVO editLog(@RequestBody LogisticsDTO dto) {
        return receiptManageService.editLog(dto);
    }

    @RequestMapping("record")
    public ResultVO record(@RequestParam(value = "rbId") Long rbId, @RequestParam(value = "status", required = false, defaultValue = "1") Byte status) {
        return receiptManageService.record(rbId, status);
    }
}
