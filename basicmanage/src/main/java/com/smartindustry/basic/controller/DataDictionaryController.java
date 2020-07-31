package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.*;
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
     * 供应商组 查询
     *
     * @return
     */
    @PostMapping("sgQuery")
    public ResultVO sgQuery() {
        return dataDictionaryService.sgQuery();
    }

    /**
     * 供应商组 编辑
     *
     * @return
     */
    @PostMapping("sgEdit")
    public ResultVO sgEdit(@RequestBody SupplierGroupDTO dto) {
        return dataDictionaryService.sgEdit(dto);
    }

    /**
     * 供应商组 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("sgDelete")
    public ResultVO sgDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.sgDelete(dto);
    }

    /**
     * 认证状态 查询
     *
     * @return
     */
    @PostMapping("csQuery")
    public ResultVO csQuery() {
        return dataDictionaryService.csQuery();
    }

    /**
     * 认证状态 编辑
     *
     * @return
     */
    @PostMapping("csEdit")
    public ResultVO csEdit(@RequestBody CertStatusDTO dto) {
        return dataDictionaryService.csEdit(dto);
    }

    /**
     * 认证状态 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("csDelete")
    public ResultVO csDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.csDelete(dto);
    }

    /**
     * 类型 查询
     *
     * @return
     */
    @PostMapping("stQuery")
    public ResultVO stQuery() {
        return dataDictionaryService.stQuery();
    }

    /**
     * 类型 编辑
     *
     * @return
     */
    @PostMapping("stEdit")
    public ResultVO stEdit(@RequestBody SupplierTypeDTO dto) {
        return dataDictionaryService.stEdit(dto);
    }

    /**
     * 类型 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("stDelete")
    public ResultVO stDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.stDelete(dto);
    }

    /**
     * 结算期限 查询
     *
     * @return
     */
    @PostMapping("spQuery")
    public ResultVO spQuery() {
        return dataDictionaryService.spQuery();
    }

    /**
     * 结算期限 编辑
     *
     * @return
     */
    @PostMapping("spEdit")
    public ResultVO spEdit(@RequestBody SettlePeriodDTO dto) {
        return dataDictionaryService.spEdit(dto);
    }

    /**
     * 结算期限 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("spDelete")
    public ResultVO spDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.spDelete(dto);
    }

    /**
     * 币种 查询
     *
     * @return
     */
    @PostMapping("currencyQuery")
    public ResultVO currencyQuery() {
        return dataDictionaryService.currencyQuery();
    }

    /**
     * 币种 编辑
     *
     * @return
     */
    @PostMapping("currencyEdit")
    public ResultVO currencyEdit(@RequestBody CurrencyDTO dto) {
        return dataDictionaryService.currencyEdit(dto);
    }

    /**
     * 币种 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("currencyDelete")
    public ResultVO currencyDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.currencyDelete(dto);
    }

    /**
     * 物料类型 查询
     *
     * @return
     */
    @PostMapping("mtQuery")
    public ResultVO mtQuery() {
        return dataDictionaryService.mtQuery();
    }

    /**
     * 物料类型 编辑
     *
     * @return
     */
    @PostMapping("mtEdit")
    public ResultVO mtEdit(@RequestBody MaterialTypeDTO dto) {
        return dataDictionaryService.mtEdit(dto);
    }

    /**
     * 物料类型 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("mtDelete")
    public ResultVO mtDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.mtDelete(dto);
    }

    /**
     * 湿度等级 查询
     *
     * @return
     */
    @PostMapping("hlQuery")
    public ResultVO hlQuery() {
        return dataDictionaryService.hlQuery();
    }

    /**
     * 湿度等级 编辑
     *
     * @return
     */
    @PostMapping("hlEdit")
    public ResultVO hlEdit(@RequestBody HumidityLevelDTO dto) {
        return dataDictionaryService.hlEdit(dto);
    }

    /**
     * 湿度等级 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("hlDelete")
    public ResultVO hlDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.hlDelete(dto);
    }

    /**
     * 物料层级 查询
     *
     * @return
     */
    @PostMapping("mlQuery")
    public ResultVO mlQuery() {
        return dataDictionaryService.mlQuery();
    }

    /**
     * 物料层级 编辑
     *
     * @return
     */
    @PostMapping("mlEdit")
    public ResultVO mlEdit(@RequestBody MaterialLevelDTO dto) {
        return dataDictionaryService.mlEdit(dto);
    }

    /**
     * 物料层级 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("mlDelete")
    public ResultVO mlDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.mlDelete(dto);
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

    /**
     * 物料版本 查询
     *
     * @return
     */
    @PostMapping("mvQuery")
    public ResultVO mvQuery() {
        return dataDictionaryService.mvQuery();
    }

    /**
     * 物料版本 编辑
     *
     * @return
     */
    @PostMapping("mvEdit")
    public ResultVO mvEdit(@RequestBody MaterialVersionDTO dto) {
        return dataDictionaryService.mvEdit(dto);
    }

    /**
     * 物料版本 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("mvDelete")
    public ResultVO mvDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.mvDelete(dto);
    }

    /**
     * 生产损耗等级 查询
     *
     * @return
     */
    @PostMapping("pllQuery")
    public ResultVO pllQuery() {
        return dataDictionaryService.pllQuery();
    }

    /**
     * 生产损耗等级 编辑
     *
     * @return
     */
    @PostMapping("pllEdit")
    public ResultVO pllEdit(@RequestBody ProduceLossLevelDTO dto) {
        return dataDictionaryService.pllEdit(dto);
    }

    /**
     * 生产损耗等级 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("pllDelete")
    public ResultVO pllDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.pllDelete(dto);
    }

    /**
     * 生命周期状态 查询
     *
     * @return
     */
    @PostMapping("lcsQuery")
    public ResultVO lcsQuery() {
        return dataDictionaryService.lcsQuery();
    }

    /**
     * 生命周期状态 编辑
     *
     * @return
     */
    @PostMapping("lcsEdit")
    public ResultVO lcsEdit(@RequestBody LifeCycleStateDTO dto) {
        return dataDictionaryService.lcsEdit(dto);
    }

    /**
     * 生命周期状态 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("lcsDelete")
    public ResultVO lcsDelete(@RequestBody BasicDataDTO dto) {
        return dataDictionaryService.lcsDelete(dto);
    }
}
