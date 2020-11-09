package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.LocationDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.ILocationService;
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
 * @description: 货位管理
 * @version: 1.0
 */
@RequestMapping("location")
@RestController
public class LocationController {
    @Autowired
    private ILocationService locationService;

    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    @PostMapping("pageQuery")
    @PreAuthorize("@ss.hasPermi('bm:wm:loc:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return locationService.pageQuery(reqData);
    }

    /**
     * 新增/修改
     *
     * @return
     */
    @PostMapping("edit")
    @PreAuthorize("@ss.hasAnyPermi('bm:wm:loc:insert,bm:wm:loc:update')")
    public ResultVO edit(@RequestBody LocationDTO dto) {
        return locationService.edit(dto);
    }

    /**
     * 删除
     *
     * @param lids
     * @return
     */
    @PostMapping("delete")
    @PreAuthorize("@ss.hasPermi('bm:wm:loc:delete')")
    public ResultVO delete(@RequestBody List<Long> lids) {
        return locationService.delete(lids);
    }

    /**
     * 查看详情
     *
     * @param dto
     * @return
     */
    @PostMapping("detail")
    @PreAuthorize("@ss.hasAnyPermi('bm:wm:loc:queryinfo,bm:wm:loc:update')")
    public ResultVO detail(@RequestBody OperateDTO dto) {
        return locationService.detail(dto);
    }

    /**
     * 货位 查询
     *
     * @return
     */
    @PostMapping("queryAll")
    public ResultVO queryAll() {
        return locationService.queryAll();
    }

    /**
     * 根据 仓库ID 查询
     *
     * @return
     */
    @PostMapping("queryByWid")
    public ResultVO queryByWid(@RequestBody OperateDTO dto) {
        return locationService.queryByWid(dto);
    }
}
