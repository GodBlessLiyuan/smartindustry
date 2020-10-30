package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.MaterialDTO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.StorageHeadDTO;
import com.smartindustry.storage.service.IPurchaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:51 2020/10/26
 * @version: 1.0.0
 * @description:
 */
@RequestMapping("storage")
@RestController
public class RawStorageController {
    @Autowired
    private IPurchaseStorageService purchaseStorageService;

    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return purchaseStorageService.pageQuery(reqData);
    }

    @PostMapping("detail")
    public ResultVO detail(@RequestBody OperateDTO dto) {
        return purchaseStorageService.detail(dto);
    }

    /**
     * 采购入库单编辑
     * @param dto
     * @return
     */
    @PostMapping("edit")
    public ResultVO edit(@RequestBody StorageHeadDTO dto){
        return purchaseStorageService.edit(dto);
    }

    /**
     * 查询所有的仓库以及储位
     * @return
     */
    @PostMapping("queryLocation")
    public ResultVO queryLocation(){
        return purchaseStorageService.queryLocation();
    }

    @PostMapping("queryMaterial")
    public ResultVO queryMaterial(@RequestBody Map<String, Object> reqData){
        return purchaseStorageService.queryMaterial(reqData);
    }

    @PostMapping("deleteBody")
    public ResultVO deleteBody(@RequestBody List<Long> sbids){
        return purchaseStorageService.deleteBody(sbids);
    }

    @PostMapping("queryStorageRecord")
    public ResultVO queryOutboundRecord(@RequestBody OperateDTO dto) {
        return purchaseStorageService.queryStorageRecord(dto);
    }
    
}
