package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.service.IReceiptManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
    private IReceiptManageService service;

    @RequestMapping("pageQuery")
    public ResultVO pageQuery(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                          @RequestParam(value = "no", required = false, defaultValue = "") String no,
                          @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                          @RequestParam(value = "status", required = false, defaultValue = "0") Byte status) {
        Map<String, Object> reqData = new HashMap<>(4);
        reqData.put("orderNo", no);
        reqData.put("keyword", keyword);
        reqData.put("status", status);

        return service.pageQuery(pageNum, pageSize, reqData);
    }
}
