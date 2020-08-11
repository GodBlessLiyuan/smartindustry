package com.smartindustry.inventory.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.service.ILocationInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 16:01
 * @description: 货位库存统计
 * @version: 1.0
 */
@RequestMapping("location")
@RestController
public class LocationInventoryController {
    @Autowired
    private ILocationInventoryService locationInventoryService;

    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return locationInventoryService.pageQuery(reqData);
    }
}
