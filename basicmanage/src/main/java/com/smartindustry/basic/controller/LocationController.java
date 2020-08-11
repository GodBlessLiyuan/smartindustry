package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.LocationDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.ILocationService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return locationService.pageQuery(reqData);
    }

    /**
     * 新增/修改
     *
     * @return
     */
    @PostMapping("edit")
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
    public ResultVO detail(@RequestBody OperateDTO dto) {
        return locationService.detail(dto);
    }

    /**
     * 操作记录
     *
     * @param dto
     * @return
     */
    @PostMapping("record")
    public ResultVO record(@RequestBody OperateDTO dto) {
        return locationService.record(dto);
    }

    /**
     * 仓库 查询
     *
     * @return
     */
    @PostMapping("queryAll")
    public ResultVO queryAll() {
        return locationService.queryAll();
    }
}
