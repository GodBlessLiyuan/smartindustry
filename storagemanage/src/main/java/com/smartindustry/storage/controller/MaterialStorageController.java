package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.StorageDetailDTO;
import com.smartindustry.storage.dto.StorageGroupDTO;
import com.smartindustry.storage.service.IMaterialStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @param pageNum  页号
     * @param pageSize 页大小
     * @param rno      收料单号
     * @param rtype    收料类型 0 全部、1 PO单收料、2 样品采购、3 生产退料
     * @param mtype    物料类型 0 全部、1 原材料、2 半成品、3 成品
     * @param status   入库状态 0 全部、1 已入库、2 入库中、3 待入库
     * @param type     入库类型 1 良品、2 非良品
     * @return
     */
    @RequestMapping("pageQuery")
    public ResultVO pageQuery(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "100000000") int pageSize,
                              @RequestParam(value = "rno", required = false, defaultValue = "") String rno,
                              @RequestParam(value = "rtype", required = false, defaultValue = "0") Byte rtype,
                              @RequestParam(value = "mtype", required = false, defaultValue = "0") Byte mtype,
                              @RequestParam(value = "status", required = false, defaultValue = "0") Byte status,
                              @RequestParam(value = "type") Byte type) {
        Map<String, Object> reqData = new HashMap<>(8);
        reqData.put("rno", rno);
        reqData.put("rtype", rtype);
        reqData.put("mtype", mtype);
        reqData.put("status", status);
        reqData.put("type", type);
        return materialStorageService.pageQuery(pageNum, pageSize, reqData);
    }

    @RequestMapping("location")
    public ResultVO location(@RequestParam(value = "lno") String lno) {
        return materialStorageService.location(lno);
    }

    @RequestMapping("label")
    public ResultVO label(@RequestBody StorageGroupDTO dto) {
        return materialStorageService.label(dto);
    }

    @RequestMapping("edit")
    public ResultVO edit(@RequestBody StorageDetailDTO dto) {
        return materialStorageService.edit(dto);
    }

    @RequestMapping("delete")
    public ResultVO delete(@RequestBody StorageDetailDTO dto) {
        return materialStorageService.delete(dto);
    }

    @RequestMapping("save")
    public ResultVO save(@RequestBody StorageGroupDTO dto) {
        return materialStorageService.save(dto);
    }

    @RequestMapping("storage")
    public ResultVO storage(@RequestParam(value = "sid") Long sid) {
        return materialStorageService.storage(sid);
    }

    @RequestMapping("detail")
    public ResultVO detail(@RequestParam("sid") Long sid) {
        return materialStorageService.detail(sid);
    }

    @RequestMapping("record")
    public ResultVO record(@RequestParam(value = "sid") Long sid) {
        return materialStorageService.record(sid);
    }
}
