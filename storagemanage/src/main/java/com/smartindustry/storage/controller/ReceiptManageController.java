package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LogisticsDTO;
import com.smartindustry.storage.dto.ReceiptDTO;
import com.smartindustry.storage.service.IReceiptManageService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("pageQuery")
    public ResultVO pageQuery(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                              @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                              @RequestParam(value = "supplier", required = false, defaultValue = "") String supplier,
                              @RequestParam(value = "ono", required = false, defaultValue = "") String ono,
                              @RequestParam(value = "status", required = false, defaultValue = "0") Byte status,
                              @RequestParam(value = "type") Byte type) {
        Map<String, Object> reqData = new HashMap<>(8);
        reqData.put("keyword", keyword);
        reqData.put("supplier", supplier);
        reqData.put("ono", ono);
        reqData.put("status", status);
        reqData.put("type", type);

        return receiptManageService.pageQuery(pageNum, pageSize, reqData);
    }

    @PostMapping("insert")
    public ResultVO insert(@RequestBody ReceiptDTO dto) {
        return receiptManageService.insert(dto);
    }

    @RequestMapping("delete")
    public ResultVO delete(@RequestParam(value = "rbids[]") List<Long> rbIds) {
        return receiptManageService.delete(rbIds);
    }

    @PostMapping("editLog")
    public ResultVO editLog(@RequestBody LogisticsDTO dto) {
        return receiptManageService.editLog(dto);
    }

    @RequestMapping("record")
    public ResultVO record(@RequestParam(value = "rbid") Long rbId, @RequestParam(value = "status", required = false, defaultValue = "1") Byte status) {
        return receiptManageService.record(rbId, status);
    }

    @RequestMapping("materialQuery")
    public ResultVO materialQuery(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                  @RequestParam(value = "mno", required = false, defaultValue = "") String mno,
                                  @RequestParam(value = "mname", required = false, defaultValue = "") String mname,
                                  @RequestParam(value = "mmodel", required = false, defaultValue = "") String mmodel) {
        Map<String, Object> reqData = new HashMap<>(4);
        reqData.put("mno", mno);
        reqData.put("mname", mname);
        reqData.put("mmodel", mmodel);

        return receiptManageService.materialQuery(pageNum, pageSize, reqData);
    }
}
