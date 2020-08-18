package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.*;
import com.smartindustry.basic.service.ISupplierService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:17
 * @description: 供应商管理
 * @version: 1.0
 */
@RequestMapping("supplier")
@RestController
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;

    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    @PostMapping("pageQuery")
    @PreAuthorize("@ss.hasPermi('bm:sm:info:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return supplierService.pageQuery(reqData);
    }

    /**
     * 新增/修改
     *
     * @return
     */
    @PostMapping("edit")
    @PreAuthorize("@ss.hasAnyPermi('bm:sm:info:insert,bm:sm:info:update')")
    public ResultVO edit(@RequestBody SupplierDTO dto) {
        return supplierService.edit(dto);
    }

    /**
     * 删除
     *
     * @param sids
     * @return
     */
    @PostMapping("delete")
    @PreAuthorize("@ss.hasPermi('bm:sm:info:delete')")
    public ResultVO delete(@RequestBody List<Long> sids) {
        return supplierService.delete(sids);
    }

    /**
     * 查看详情
     *
     * @param dto
     * @return
     */
    @PostMapping("detail")
    @PreAuthorize("@ss.hasAnyPermi('bm:sm:info:queryinfo,bm:sm:info:update')")
    public ResultVO detail(@RequestBody OperateDTO dto) {
        return supplierService.detail(dto);
    }

    /**
     * 操作记录
     *
     * @param dto
     * @return
     */
    @PostMapping("record")
    public ResultVO record(@RequestBody OperateDTO dto) {
        return supplierService.record(dto);
    }

    /**
     * 供应商 查询
     *
     * @return
     */
    @PostMapping("queryAll")
    public ResultVO queryAll() {
        return supplierService.queryAll();
    }

}
