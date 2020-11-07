package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.ISupplierService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 供应商 查询
     *
     * @return
     */
    @PostMapping("queryAll")
    public ResultVO queryAll() {
        return supplierService.queryAll();
    }

}
