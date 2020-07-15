package com.smartindustry.outbound.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.dto.PickDTO;
import com.smartindustry.outbound.service.IErpExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/7/15 15:08
 * @description: ERP 外部接口
 * @version: 1.0
 */
@RequestMapping("erp")
@RestController
public class ErpExternalController {
    @Autowired
    private IErpExternalService erpExternalService;

    @RequestMapping("pick")
    public ResultVO pick(@RequestBody PickDTO dto) {
        return erpExternalService.pick(dto);
    }
}
