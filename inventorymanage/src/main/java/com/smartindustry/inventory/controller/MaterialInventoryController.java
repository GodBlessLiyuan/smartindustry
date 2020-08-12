package com.smartindustry.inventory.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.service.IMaterialInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 16:02
 * @description: 物料库存统计
 * @version: 1.0
 */
@RequestMapping("material")
@RestController
public class MaterialInventoryController {
    @Autowired
    private IMaterialInventoryService materialInventoryService;

    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        System.out.println("MATERIAL - PAGEQUERY");
        return materialInventoryService.pageQuery(reqData);
    }
}
