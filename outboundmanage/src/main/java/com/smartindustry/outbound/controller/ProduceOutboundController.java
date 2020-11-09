package com.smartindustry.outbound.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.service.IProduceOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:24 2020/11/8
 * @version: 1.0.0
 * @description:
 */
@RequestMapping("outboundP")
@RestController
public class ProduceOutboundController {
    @Autowired
    private IProduceOutboundService produceOutboundService;
    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return produceOutboundService.pageQuery(reqData);
    }
}
