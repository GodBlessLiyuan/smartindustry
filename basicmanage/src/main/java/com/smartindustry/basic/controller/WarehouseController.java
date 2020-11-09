package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.dto.WarehouseDTO;
import com.smartindustry.basic.service.IWarehouseService;
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
 * @description: 仓库管理
 * @version: 1.0
 */
@RequestMapping("warehouse")
@RestController
public class WarehouseController {
    @Autowired
    private IWarehouseService warehouseService;

    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    @PostMapping("pageQuery")
    @PreAuthorize("@ss.hasPermi('bm:wm:whfile:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return warehouseService.pageQuery(reqData);
    }

    /**
     * 新增/修改
     *
     * @return
     */
    @PostMapping("edit")
    @PreAuthorize("@ss.hasAnyPermi('bm:wm:whfile:insert,bm:wm:whfile:update')")
    public ResultVO edit(@RequestBody WarehouseDTO dto) {
        return warehouseService.edit(dto);
    }

    /**
     * 删除
     *
     * @param wids
     * @return
     */
    @PostMapping("delete")
    @PreAuthorize("@ss.hasPermi('bm:wm:whfile:delete')")
    public ResultVO delete(@RequestBody List<Long> wids) {
        return warehouseService.delete(wids);
    }

    /**
     * 查看详情
     *
     * @param dto
     * @return
     */
    @PostMapping("detail")
    @PreAuthorize("@ss.hasAnyPermi('bm:wm:whfile:queryinfo,bm:wm:whfile:update')")
    public ResultVO detail(@RequestBody OperateDTO dto) {
        return warehouseService.detail(dto);
    }
    

    /**
     * 仓库 查询
     *
     * @return
     */
    @PostMapping("queryAll")
    public ResultVO queryAll() {
        return warehouseService.queryAll();
    }

}
