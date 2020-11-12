package com.smartindustry.basic.service.impl;

import com.smartindustry.basic.dto.BasicDataDTO;
import com.smartindustry.basic.dto.LocationTypeDTO;
import com.smartindustry.basic.dto.MeasureUnitDTO;
import com.smartindustry.basic.dto.WarehouseTypeDTO;
import com.smartindustry.basic.service.IDataDictionaryService;
import com.smartindustry.common.mapper.dd.LocationTypeMapper;
import com.smartindustry.common.mapper.dd.MeasureUnitMapper;
import com.smartindustry.common.mapper.dd.WarehouseTypeMapper;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.si.WarehouseMapper;
import com.smartindustry.common.pojo.dd.LocationTypePO;
import com.smartindustry.common.pojo.dd.MeasureUnitPO;
import com.smartindustry.common.pojo.dd.WarehouseTypePO;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.si.MaterialPO;
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
public class DataDictionaryServiceImpl implements IDataDictionaryService {

    @Autowired
    private WarehouseTypeMapper warehouseTypeMapper;

    @Autowired
    private LocationTypeMapper locationTypeMapper;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private MeasureUnitMapper measureUnitMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private MaterialMapper materialMapper;

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
    public ResultVO ltQuery() {
        List<Map<String, Object>> res = locationTypeMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO ltEdit(LocationTypeDTO dto) {
        LocationTypePO exitPO = locationTypeMapper.queryByName(dto.getLtname());
        if (null != exitPO && (dto.getLtid() == null || !dto.getLtid().equals(exitPO.getLocationTypeId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getLtid()) {
            // 新增
            locationTypeMapper.insert(LocationTypeDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        LocationTypePO locationTypePO = locationTypeMapper.selectByPrimaryKey(dto.getLtid());
        if (null == locationTypePO) {
            return new ResultVO(1002);
        }

        locationTypeMapper.updateByPrimaryKey(LocationTypeDTO.buildPO(locationTypePO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO ltDelete(BasicDataDTO dto) {
        LocationTypePO locationTypePO = locationTypeMapper.selectByPrimaryKey(dto.getLtid());
        if (null == locationTypePO) {
            return new ResultVO(1002);
        }

        List<LocationPO> locationPOs = locationMapper.queryByLtid(dto.getLtid());
        if (null != locationPOs && locationPOs.size() > 0) {
            return new ResultVO(1007);
        }

        locationTypeMapper.deleteByPrimaryKey(dto.getLtid());

        return ResultVO.ok();
    }

    @Override
    public ResultVO muQuery() {
        List<Map<String, Object>> res = measureUnitMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO muEdit(MeasureUnitDTO dto) {
        MeasureUnitPO exitPO = measureUnitMapper.queryByName(dto.getMuname());
        if (null != exitPO && (dto.getMuid() == null || !dto.getMuid().equals(exitPO.getMeasureUnitId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getMuid()) {
            // 新增
            measureUnitMapper.insert(MeasureUnitDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        MeasureUnitPO measureUnitPO = measureUnitMapper.selectByPrimaryKey(dto.getMuid());
        if (null == measureUnitPO) {
            return new ResultVO(1002);
        }

        measureUnitMapper.updateByPrimaryKey(MeasureUnitDTO.buildPO(measureUnitPO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO muDelete(BasicDataDTO dto) {
        MeasureUnitPO measureUnitPO = measureUnitMapper.selectByPrimaryKey(dto.getMuid());
        if (null == measureUnitPO) {
            return new ResultVO(1002);
        }

        List<MaterialPO> materialPOs = materialMapper.queryByMuid(dto.getMuid());
        if (null != materialPOs && materialPOs.size() > 0) {
            return new ResultVO(1007);
        }

        measureUnitMapper.deleteByPrimaryKey(dto.getMuid());

        return ResultVO.ok();
    }

    @Override
    public ResultVO maQuery() {
        List<Map<String,Object>> vos=materialMapper.listAll();
        return new ResultVO(1000,vos);
    }
}
