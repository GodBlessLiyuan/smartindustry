package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IClientService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:10 2020/9/16
 * @version: 1.0.0
 * @description:
 */
@RequestMapping("client")
@RestController
public class ClientController {
    @Autowired
    IClientService clientService;


    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return clientService.pageQuery(reqData);
    }

    @PostMapping("query")
    public ResultVO query(@RequestBody OperateDTO dto) {
        return clientService.query(dto);
    }

}
