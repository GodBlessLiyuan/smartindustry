package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.OutboundHeadDTO;
import com.smartindustry.storage.service.IOutBoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:14 2020/10/28
 * @version: 1.0.0
 * @description:
 */
@RequestMapping("outbound")
@RestController
public class OutBoundController {
    @Autowired
    private IOutBoundService outBoundService;

    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return outBoundService.pageQuery(reqData);
    }

    /**
     *  混料工单信息
     * @return
     */
    @PostMapping("queryMix")
    public ResultVO queryMix() {
        return outBoundService.queryMix();
    }

    @PostMapping("edit")
    public ResultVO edit(@RequestBody OutboundHeadDTO dto) {
        return outBoundService.edit(dto);
    }

    @PostMapping("detail")
    public ResultVO detail(@RequestBody OperateDTO dto) {
        return outBoundService.detail(dto);
    }

    @PostMapping("queryOutboundRecord")
    public ResultVO queryOutboundRecord(@RequestBody OperateDTO dto) {
        return outBoundService.queryOutboundRecord(dto);
    }
}
