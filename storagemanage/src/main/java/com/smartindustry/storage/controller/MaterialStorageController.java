package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.StorageDetailDTO;
import com.smartindustry.storage.dto.StorageGroupDTO;
import com.smartindustry.storage.service.IMaterialStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PreAuthorize("@ss.hasAnyPermi('sm:ms:gpwl:query,sm:ms:ndgr:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return materialStorageService.pageQuery(reqData);
    }

    @PostMapping("queryInfo")
    public ResultVO queryInfo(@RequestBody OperateDTO dto) {
        return materialStorageService.queryInfo(dto);
    }

    @PostMapping("queryPidInfo")
    public ResultVO queryPidInfo(@RequestBody Map<String, Object> reqData){
        return materialStorageService.queryPidInfo(reqData);
    }

    /**
     * 查询入库单的已入库情况查询
     * @param dto
     * @return
     */
    @PostMapping("storageDetail4Sid")
    @PreAuthorize("@ss.hasAnyPermi('sm:ms:gpwl:queryinfo,sm:ms:ndgr:queryinfo')")
    public ResultVO storageDetail4Sid(@RequestBody OperateDTO dto) {
        return materialStorageService.storageDetail4Sid(dto);
    }

    @PostMapping("agreeStorage")
    public ResultVO agreeStorage(@RequestBody OperateDTO dto){
        return materialStorageService.agreeStorage(dto);
    }

    /**
     * 其他入库单 分页查询
     *
     * @return
     */
    @PostMapping("pageQueryOther")
    @PreAuthorize("@ss.hasAnyPermi('sm:ms:gpwl:query,sm:ms:ndgr:query')")
    public ResultVO pageQueryOther(@RequestBody Map<String, Object> reqData) {
        return materialStorageService.pageQueryOther(reqData);
    }

    @PostMapping("queryBySid")
    public ResultVO queryBySid(@RequestBody OperateDTO dto){
        return materialStorageService.queryBySid(dto);
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
    @PreAuthorize("@ss.hasAnyPermi('sm:ms:gpwl:scan,sm:ms:ndgr:scan')")
    public ResultVO save(@RequestBody StorageGroupDTO dto) {
        return materialStorageService.save(dto);
    }

    @PostMapping("storage")
    @PreAuthorize("@ss.hasAnyPermi('sm:ms:gpwl:scan,sm:ms:ndgr:scan')")
    public ResultVO storage(@RequestBody OperateDTO dto) {
        return materialStorageService.storage(dto);
    }

    @PostMapping("detail")
    @PreAuthorize("@ss.hasAnyPermi('sm:ms:gpwl:queryinfo,sm:ms:ndgr:queryinfo')")
    public ResultVO detail(@RequestBody OperateDTO dto) {
        return materialStorageService.detail(dto);
    }

    @PostMapping("record")
    public ResultVO record(@RequestBody OperateDTO dto) {
        return materialStorageService.record(dto);
    }

    @PostMapping("storageScan")
    ResultVO storageScan(@RequestBody OperateDTO dto){
        return materialStorageService.storageScan(dto);
    }

    @PostMapping("storageDelete")
    ResultVO storageDelete(@RequestBody StorageDetailDTO dto){
        return materialStorageService.storageDelete(dto);
    }

    @PostMapping("storageSave")
    ResultVO storageSave(@RequestBody StorageGroupDTO dto){
        return materialStorageService.storageSave(dto);
    }

    @PostMapping("storageAgree")
    ResultVO storageAgree(@RequestBody OperateDTO dto){
        return materialStorageService.storageAgree(dto);
    }

    @PostMapping("storageDetail")
    ResultVO storageDetail(@RequestBody OperateDTO dto){
        return materialStorageService.storageDetail(dto);
    }

    /**
     * 当选择仓库发生变化时 清空所有已经入库的数据
     *
     * @param dto
     * @return
     */
    @PostMapping("changeWarehouse")
    public ResultVO changeWarehouse(@RequestBody OperateDTO dto) {
        return materialStorageService.changeWarehouse(dto);
    }

    /**
     * 通过入库详情组ID 查询标签列表
     * @param dto
     * @return
     */
    @PostMapping("queryBySgId")
    public ResultVO queryBySgid(@RequestBody OperateDTO dto) {
        return materialStorageService.queryBySgid(dto);
    }

}
