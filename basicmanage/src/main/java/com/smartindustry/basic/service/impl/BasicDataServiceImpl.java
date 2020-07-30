package com.smartindustry.basic.service.impl;

import com.smartindustry.basic.dto.*;
import com.smartindustry.basic.service.IBasicDataService;
import com.smartindustry.common.mapper.dd.*;
import com.smartindustry.common.mapper.si.SupplierMapper;
import com.smartindustry.common.mapper.si.WarehouseMapper;
import com.smartindustry.common.pojo.dd.*;
import com.smartindustry.common.pojo.si.SupplierPO;
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
        List<Map<String, Object>> res = supplierGroupMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO sgEdit(SupplierGroupDTO dto) {
        SupplierGroupPO exitPO = supplierGroupMapper.queryByName(dto.getSgname());
        if (null != exitPO && (dto.getSgid() == null || !dto.getSgid().equals(exitPO.getSupplierGroupId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getSgid()) {
            // 新增
            supplierGroupMapper.insert(SupplierGroupDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        SupplierGroupPO supplierGroupPO = supplierGroupMapper.selectByPrimaryKey(dto.getSgid());
        if (null == supplierGroupPO) {
            return new ResultVO(1002);
        }

        supplierGroupMapper.updateByPrimaryKey(SupplierGroupDTO.buildPO(supplierGroupPO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO sgDelete(BasicDataDTO dto) {
        SupplierGroupPO supplierGroupPO = supplierGroupMapper.selectByPrimaryKey(dto.getSgid());
        if (null == supplierGroupPO) {
            return new ResultVO(1002);
        }

        List<SupplierPO> supplierPOs = supplierMapper.queryBySgid(dto.getSgid());
        if (null != supplierPOs && supplierPOs.size() > 0) {
            return new ResultVO(1007);
        }

        supplierGroupMapper.deleteByPrimaryKey(dto.getSgid());

        return ResultVO.ok();
    }

    @Override
    public ResultVO csQuery() {
        List<Map<String, Object>> res = certStatusMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO csEdit(CertStatusDTO dto) {
        CertStatusPO exitPO = certStatusMapper.queryByName(dto.getCsname());
        if (null != exitPO && (dto.getCsid() == null || !dto.getCsid().equals(exitPO.getCertStatusId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getCsid()) {
            // 新增
            certStatusMapper.insert(CertStatusDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        CertStatusPO certStatusPO = certStatusMapper.selectByPrimaryKey(dto.getCsid());
        if (null == certStatusPO) {
            return new ResultVO(1002);
        }

        certStatusMapper.updateByPrimaryKey(CertStatusDTO.buildPO(certStatusPO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO csDelete(BasicDataDTO dto) {
        CertStatusPO certStatusPO = certStatusMapper.selectByPrimaryKey(dto.getCsid());
        if (null == certStatusPO) {
            return new ResultVO(1002);
        }

        List<SupplierPO> supplierPOs = supplierMapper.queryByCsid(dto.getCsid());
        if (null != supplierPOs && supplierPOs.size() > 0) {
            return new ResultVO(1007);
        }

        certStatusMapper.deleteByPrimaryKey(dto.getCsid());

        return ResultVO.ok();
    }

    @Override
    public ResultVO stQuery() {
        List<Map<String, Object>> res = supplierTypeMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO stEdit(SupplierTypeDTO dto) {
        SupplierTypePO exitPO = supplierTypeMapper.queryByName(dto.getStname());
        if (null != exitPO && (dto.getStid() == null || !dto.getStid().equals(exitPO.getSupplierTypeId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getStid()) {
            // 新增
            supplierTypeMapper.insert(SupplierTypeDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        SupplierTypePO supplierTypePO = supplierTypeMapper.selectByPrimaryKey(dto.getStid());
        if (null == supplierTypePO) {
            return new ResultVO(1002);
        }

        supplierTypeMapper.updateByPrimaryKey(SupplierTypeDTO.buildPO(supplierTypePO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO stDelete(BasicDataDTO dto) {
        SupplierTypePO supplierTypePO = supplierTypeMapper.selectByPrimaryKey(dto.getStid());
        if (null == supplierTypePO) {
            return new ResultVO(1002);
        }

        List<SupplierPO> supplierPOs = supplierMapper.queryByStid(dto.getStid());
        if (null != supplierPOs && supplierPOs.size() > 0) {
            return new ResultVO(1007);
        }

        supplierTypeMapper.deleteByPrimaryKey(dto.getStid());

        return ResultVO.ok();
    }

    @Override
    public ResultVO spQuery() {
        List<Map<String, Object>> res = settlePeriodMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO spEdit(SettlePeriodDTO dto) {
        SettlePeriodPO exitPO = settlePeriodMapper.queryByName(dto.getSpname());
        if (null != exitPO && (dto.getSpid() == null || !dto.getSpid().equals(exitPO.getSettlePeriodId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getSpid()) {
            // 新增
            settlePeriodMapper.insert(SettlePeriodDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        SettlePeriodPO settlePeriodPO = settlePeriodMapper.selectByPrimaryKey(dto.getSpid());
        if (null == settlePeriodPO) {
            return new ResultVO(1002);
        }

        settlePeriodMapper.updateByPrimaryKey(SettlePeriodDTO.buildPO(settlePeriodPO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO spDelete(BasicDataDTO dto) {
        SettlePeriodPO settlePeriodPO = settlePeriodMapper.selectByPrimaryKey(dto.getSpid());
        if (null == settlePeriodPO) {
            return new ResultVO(1002);
        }

        List<SupplierPO> supplierPOs = supplierMapper.queryBySpid(dto.getSpid());
        if (null != supplierPOs && supplierPOs.size() > 0) {
            return new ResultVO(1007);
        }

        settlePeriodMapper.deleteByPrimaryKey(dto.getSpid());

        return ResultVO.ok();
    }

    @Override
    public ResultVO currencyQuery() {
        List<Map<String, Object>> res = currencyMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO currencyEdit(CurrencyDTO dto) {
        CurrencyPO exitPO = currencyMapper.queryByName(dto.getCname());
        if (null != exitPO && (dto.getCid() == null || !dto.getCid().equals(exitPO.getCurrencyId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getCid()) {
            // 新增
            currencyMapper.insert(CurrencyDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        CurrencyPO currencyPO = currencyMapper.selectByPrimaryKey(dto.getCid());
        if (null == currencyPO) {
            return new ResultVO(1002);
        }

        currencyMapper.updateByPrimaryKey(CurrencyDTO.buildPO(currencyPO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO currencyDelete(BasicDataDTO dto) {
        CurrencyPO currencyPO = currencyMapper.selectByPrimaryKey(dto.getCid());
        if (null == currencyPO) {
            return new ResultVO(1002);
        }

        List<SupplierPO> supplierPOs = supplierMapper.queryByCid(dto.getCid());
        if (null != supplierPOs && supplierPOs.size() > 0) {
            return new ResultVO(1007);
        }

        currencyMapper.deleteByPrimaryKey(dto.getCid());

        return ResultVO.ok();
    }
}
