package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.service.IErpExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/30 9:11
 * @description: ERP 外部接口
 * @version: 1.0
 */
@RequestMapping("erp")
@RestController
public class ErpExternalController {
    @Autowired
    private IErpExternalService erpExternalService;

    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return erpExternalService.pageQuery(reqData);
    }

    @PostMapping("order")
    @PreAuthorize("@ss.hasPermi('sm:rm:prm:insert')")
    public ResultVO order(@RequestBody Map<String, Object> reqData) {
        return erpExternalService.order(reqData);
    }
}
