package com.smartindustry.basic.service.impl;

import com.smartindustry.basic.dto.*;
import com.smartindustry.basic.service.IBasicDataService;
import com.smartindustry.common.mapper.dd.*;
import com.smartindustry.common.mapper.si.SupplierMapper;
import com.smartindustry.common.mapper.si.WarehouseMapper;
import com.smartindustry.common.pojo.dd.SettlePeriodPO;
import com.smartindustry.common.pojo.dd.WarehouseTypePO;
import com.smartindustry.common.pojo.si.WarehousePO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 14:42
 * @description: 基础数据
 * @version: 1.0
 */
@Service
public class BasicDataServiceImpl implements IBasicDataService {
    @Autowired
    private WarehouseMapper warehouseMapper;
    @Autowired
    private WarehouseTypeMapper warehouseTypeMapper;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private SupplierGroupMapper supplierGroupMapper;
    @Autowired
    private CertStatusMapper certStatusMapper;
    @Autowired
    private SupplierTypeMapper supplierTypeMapper;
    @Autowired
    private SettlePeriodMapper settlePeriodMapper;
    @Autowired
    private CurrencyMapper currencyMapper;

    @Override
    public ResultVO wtQuery() {
        List<Map<String, Object>> res = warehouseTypeMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO wtEdit(WarehouseTypeDTO dto) {
        WarehouseTypePO exitPO = warehouseTypeMapper.queryByName(dto.getWtname());
        if (null != exitPO && (dto.getWtid() == null || !dto.getWtid().equals(exitPO.getWarehouseTypeId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getWtid()) {
            // 新增
            warehouseTypeMapper.insert(WarehouseTypeDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        WarehouseTypePO warehouseTypePO = warehouseTypeMapper.selectByPrimaryKey(dto.getWtid());
        if (null == warehouseTypePO) {
            return new ResultVO(1002);
        }

        warehouseTypeMapper.updateByPrimaryKey(WarehouseTypeDTO.buildPO(warehouseTypePO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO wtDelete(BasicDataDTO dto) {
        WarehouseTypePO warehouseTypePO = warehouseTypeMapper.selectByPrimaryKey(dto.getWtid());
        if (null == warehouseTypePO) {
            return new ResultVO(1002);
        }

        List<WarehousePO> warehousePOs = warehouseMapper.queryByWtid(dto.getWtid());
        if (null != warehousePOs && warehousePOs.size() > 0) {
            return new ResultVO(1007);
        }

        warehouseTypeMapper.deleteByPrimaryKey(dto.getWtid());

        return ResultVO.ok();
    }

    @Override
    public ResultVO sgQuery() {
        return null;
    }

    @Override
    public ResultVO sgEdit(SupplierGroupDTO dto) {
        return null;
    }

    @Override
    public ResultVO sgDelete(BasicDataDTO dto) {
        return null;
    }

    @Override
    public ResultVO csQuery() {
        return null;
    }

    @Override
    public ResultVO csEdit(CertStatusDTO dto) {
        return null;
    }

    @Override
    public ResultVO csDelete(BasicDataDTO dto) {
        return null;
    }

    @Override
    public ResultVO stQuery() {
        return null;
    }

    @Override
    public ResultVO stEdit(SupplierTypeDTO dto) {
        return null;
    }

    @Override
    public ResultVO stDelete(BasicDataDTO dto) {
        return null;
    }

    @Override
    public ResultVO spQuery() {
        return null;
    }

    @Override
    public ResultVO spEdit(WarehouseTypeDTO dto) {
        return null;
    }

    @Override
    public ResultVO spDelete(BasicDataDTO dto) {
        return null;
    }

    @Override
    public ResultVO currencyQuery() {
        return null;
    }

    @Override
    public ResultVO currencyEdit(WarehouseTypeDTO dto) {
        return null;
    }

    @Override
    public ResultVO currencyDelete(BasicDataDTO dto) {
        return null;
    }
}
