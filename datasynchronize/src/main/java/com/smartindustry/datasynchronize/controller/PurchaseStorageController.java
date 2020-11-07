package com.smartindustry.datasynchronize.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.datasynchronize.service.IPurchaseStorageService;
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
@RequestMapping("purchase")
@RestController
public class PurchaseStorageController {

    @Resource
    private IPurchaseStorageService purchaseStorageService;

    @PostMapping("sync")
    public ResultVO sync(@RequestBody Map<String, Object> reqData) {
        return  purchaseStorageService.sync(reqData);
    }
}
