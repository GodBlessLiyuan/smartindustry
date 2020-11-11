package com.smartindustry.inventory.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.dto.SafeStockDTO;
import com.smartindustry.inventory.service.IMaterialInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: jiangchaojie
 * @date: Created in 2020/11/11
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
        return materialInventoryService.pageQuery(reqData);
    }

    /**
     * 设置安全库存
     */
    @PostMapping("safeStock")
    public ResultVO safeStock(@RequestBody SafeStockDTO dto) {
        return materialInventoryService.safeStock(dto);
    }

    /**
     * 成品库存明细查询分页查询
     *
     * @return
     */
    @PostMapping("pageQueryPro")
    public ResultVO pageQueryPro(@RequestBody Map<String, Object> reqData) {
        return materialInventoryService.pageQueryPro(reqData);
    }
}
