package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.ClientDTO;
import com.smartindustry.basic.dto.ClientTypeDTO;
import com.smartindustry.basic.dto.CreditLevelDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IClientService;
import com.smartindustry.basic.service.IDataDictionaryService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    @Autowired
    IDataDictionaryService dataDictionaryService;

    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return clientService.pageQuery(reqData);
    }

    @PostMapping("clInsert")
    public ResultVO clInsert(@RequestBody CreditLevelDTO dto) {
        return dataDictionaryService.clInsert(dto);
    }

    @PostMapping("ctInsert")
    public ResultVO ctInsert(@RequestBody ClientTypeDTO dto) {
        return dataDictionaryService.ctInsert(dto);
    }

    @PostMapping("clQuery")
    public ResultVO clQuery() {
        return dataDictionaryService.clQuery();
    }

    @PostMapping("ctQuery")
    public ResultVO ctQuery() {
        return dataDictionaryService.ctQuery();
    }

    @PostMapping("delete")
    public ResultVO delete(@RequestBody List<Long> cids) {
        return clientService.delete(cids);
    }

    @PostMapping("edit")
    public ResultVO edit(@RequestBody ClientDTO dto) {
        return clientService.edit(dto);
    }

    @PostMapping("queryRecord")
    public ResultVO queryRecord(@RequestBody OperateDTO dto) {
        return clientService.queryRecord(dto);
    }

    @PostMapping("query")
    public ResultVO query(@RequestBody OperateDTO dto) {
        return clientService.query(dto);
    }

}
