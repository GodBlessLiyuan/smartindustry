package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.StorageDetailDTO;
import com.smartindustry.storage.service.IProduceStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:57 2020/11/4
 * @version: 1.0.0
 * @description:
 */
@RequestMapping("storageP")
@RestController
public class ProduceStorageController {
    @Autowired
    private IProduceStorageService produceStorageService;
    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return produceStorageService.pageQuery(reqData);
    }

    @PostMapping("detail")
    public ResultVO detail(@RequestBody OperateDTO dto) {
        return produceStorageService.detail(dto);
    }

    @PostMapping("queryDetail")
    public ResultVO queryDetail(@RequestBody StorageDetailDTO dto) {
        return produceStorageService.queryDetail(dto);
    }

    @PostMapping("queryStorageRecord")
    public ResultVO queryOutboundRecord(@RequestBody OperateDTO dto) {
        return produceStorageService.queryStorageRecord(dto);
    }
}
