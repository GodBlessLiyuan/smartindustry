package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.ISalesOutboundService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author hui.feng
 * @date created in 2020/11/5
 * @description
 */
@RestController
@RequestMapping("sales")
public class SalesOutboundController {

    @Resource
    private ISalesOutboundService salesOutboundService;

    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return salesOutboundService.pageQuery(reqData);
    }

    @PostMapping("detail")
    public ResultVO detail(@RequestBody OperateDTO dto) {
        return salesOutboundService.detail(dto);
    }
}
