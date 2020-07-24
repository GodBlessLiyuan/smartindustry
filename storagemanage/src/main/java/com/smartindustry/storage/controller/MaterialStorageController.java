package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.StorageDetailDTO;
import com.smartindustry.storage.dto.StorageGroupDTO;
import com.smartindustry.storage.service.IMaterialStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 11:40
 * @description: 物料入库
 * @version: 1.0
 */
@RequestMapping("storage")
@RestController
public class MaterialStorageController {
    @Autowired
    private IMaterialStorageService materialStorageService;

    /**
     * 入库单 分页查询
     *
     * @return
     */
    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return materialStorageService.pageQuery(reqData);
    }

    @PostMapping("location")
    public ResultVO location(@RequestBody OperateDTO dto) {
        return materialStorageService.location(dto);
    }

    @PostMapping("label")
    public ResultVO label(@RequestBody StorageGroupDTO dto) {
        return materialStorageService.label(dto);
    }

    @PostMapping("edit")
    public ResultVO edit(@RequestBody StorageDetailDTO dto) {
        return materialStorageService.edit(dto);
    }

    @PostMapping("delete")
    public ResultVO delete(@RequestBody StorageDetailDTO dto) {
        return materialStorageService.delete(dto);
    }

    @PostMapping("save")
    public ResultVO save(@RequestBody StorageGroupDTO dto) {
        return materialStorageService.save(dto);
    }

    @PostMapping("storage")
    public ResultVO storage(@RequestBody OperateDTO dto) {
        return materialStorageService.storage(dto);
    }

    @PostMapping("detail")
    public ResultVO detail(@RequestBody OperateDTO dto) {
        return materialStorageService.detail(dto);
    }

    @PostMapping("record")
    public ResultVO record(@RequestBody OperateDTO dto) {
        return materialStorageService.record(dto);
    }
}
