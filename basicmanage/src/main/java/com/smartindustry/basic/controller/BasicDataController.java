package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.*;
import com.smartindustry.basic.service.IBasicDataService;
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
@RequestMapping("basic")
@RestController
public class BasicDataController {
    @Autowired
    private IBasicDataService basicDataService;

    /**
     * 仓库类型 查询
     *
     * @return
     */
    @PostMapping("wtQuery")
    public ResultVO wtQuery() {
        return basicDataService.wtQuery();
    }

    /**
     * 仓库类型 编辑
     *
     * @return
     */
    @PostMapping("wtEdit")
    public ResultVO wtEdit(@RequestBody WarehouseTypeDTO dto) {
        return basicDataService.wtEdit(dto);
    }

    /**
     * 仓库类型 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("wtDelete")
    public ResultVO wtDelete(@RequestBody BasicDataDTO dto) {
        return basicDataService.wtDelete(dto);
    }


    /**
     * 供应商组 查询
     *
     * @return
     */
    @PostMapping("sgQuery")
    public ResultVO sgQuery() {
        return basicDataService.sgQuery();
    }

    /**
     * 供应商组 编辑
     *
     * @return
     */
    @PostMapping("sgEdit")
    public ResultVO sgEdit(@RequestBody SupplierGroupDTO dto) {
        return basicDataService.sgEdit(dto);
    }

    /**
     * 供应商组 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("sgDelete")
    public ResultVO sgDelete(@RequestBody BasicDataDTO dto) {
        return basicDataService.sgDelete(dto);
    }

    /**
     * 认证状态 查询
     *
     * @return
     */
    @PostMapping("csQuery")
    public ResultVO csQuery() {
        return basicDataService.csQuery();
    }

    /**
     * 认证状态 编辑
     *
     * @return
     */
    @PostMapping("csEdit")
    public ResultVO csEdit(@RequestBody CertStatusDTO dto) {
        return basicDataService.csEdit(dto);
    }

    /**
     * 认证状态 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("csDelete")
    public ResultVO csDelete(@RequestBody BasicDataDTO dto) {
        return basicDataService.csDelete(dto);
    }

    /**
     * 类型 查询
     *
     * @return
     */
    @PostMapping("stQuery")
    public ResultVO stQuery() {
        return basicDataService.stQuery();
    }

    /**
     * 类型 编辑
     *
     * @return
     */
    @PostMapping("stEdit")
    public ResultVO stEdit(@RequestBody SupplierTypeDTO dto) {
        return basicDataService.stEdit(dto);
    }

    /**
     * 类型 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("stDelete")
    public ResultVO stDelete(@RequestBody BasicDataDTO dto) {
        return basicDataService.stDelete(dto);
    }

    /**
     * 结算期限 查询
     *
     * @return
     */
    @PostMapping("spQuery")
    public ResultVO spQuery() {
        return basicDataService.spQuery();
    }

    /**
     * 结算期限 编辑
     *
     * @return
     */
    @PostMapping("spEdit")
    public ResultVO spEdit(@RequestBody SettlePeriodDTO dto) {
        return basicDataService.spEdit(dto);
    }

    /**
     * 结算期限 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("spDelete")
    public ResultVO spDelete(@RequestBody BasicDataDTO dto) {
        return basicDataService.spDelete(dto);
    }

    /**
     * 币种 查询
     *
     * @return
     */
    @PostMapping("currencyQuery")
    public ResultVO currencyQuery() {
        return basicDataService.currencyQuery();
    }

    /**
     * 币种 编辑
     *
     * @return
     */
    @PostMapping("currencyEdit")
    public ResultVO currencyEdit(@RequestBody CurrencyDTO dto) {
        return basicDataService.currencyEdit(dto);
    }

    /**
     * 币种 删除
     *
     * @param dto
     * @return
     */
    @PostMapping("currencyDelete")
    public ResultVO currencyDelete(@RequestBody BasicDataDTO dto) {
        return basicDataService.currencyDelete(dto);
    }
}
