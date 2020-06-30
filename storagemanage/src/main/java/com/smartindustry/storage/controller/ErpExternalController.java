package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.service.IErpExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("order")
    public ResultVO order(@RequestParam(value = "ono") String ono, @RequestParam(value = "otype") Byte otype) {
        return erpExternalService.order(ono, otype);
    }
}
