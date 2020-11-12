package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.BasicDataDTO;
import com.smartindustry.basic.dto.LocationTypeDTO;
import com.smartindustry.basic.dto.MeasureUnitDTO;
import com.smartindustry.basic.dto.WarehouseTypeDTO;
import com.smartindustry.basic.service.IDataDictionaryService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 14:41
 * @description: 基础数据
 * @version: 1.0
 */
@RequestMapping("data")
@RestController
public class DataDictionaryController {
    @Autowired
    private IDataDictionaryService dataDictionaryService;

    /**
     * 仓库类型 查询
     *
     * @return
     */
    @PostMapping("wtQuery")
    public ResultVO wtQuery() {
        return dataDictionaryService.wtQuery();
    }

    /**
     * 仓库类型 编辑
     *
     * @return
     */
    @PostMapping("wtEdit")
    public ResultVO wtEdit(@RequestBody WarehouseTypeDTO dto) {
        return dataDictionaryService.wtEdit(dto);
    }

    /**
     * 仓库类型 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("wtDelete")
    public ResultVO wtDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.wtDelete(dto);
    }

    /**
     * 货位类型 查询
     *
     * @return
     */
    @PostMapping("ltQuery")
    public ResultVO ltQuery() {
        return dataDictionaryService.ltQuery();
    }

    /**
     * 货位类型 编辑
     *
     * @return
     */
    @PostMapping("ltEdit")
    public ResultVO ltEdit(@RequestBody LocationTypeDTO dto) {
        return dataDictionaryService.ltEdit(dto);
    }

    /**
     * 货位类型 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("ltDelete")
    public ResultVO ltDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.ltDelete(dto);
    }

    /**
     * 计量单位 查询
     *
     * @return
     */
    @PostMapping("muQuery")
    public ResultVO muQuery() {
        return dataDictionaryService.muQuery();
    }

    /**
     * 计量单位 编辑
     *
     * @return
     */
    @PostMapping("muEdit")
    public ResultVO muEdit(@RequestBody MeasureUnitDTO dto) {
        return dataDictionaryService.muEdit(dto);
    }

    /**
     * 计量单位 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("muDelete")
    public ResultVO muDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.muDelete(dto);
    }

    /***
     * 查询物料类型
     * @return
     */
    @PostMapping("maQuery")
    public ResultVO maQuery(){
        return dataDictionaryService.maQuery();
    }

}
