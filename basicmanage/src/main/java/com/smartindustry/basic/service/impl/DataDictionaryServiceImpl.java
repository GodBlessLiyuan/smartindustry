package com.smartindustry.basic.service.impl;

import com.smartindustry.basic.dto.*;
import com.smartindustry.basic.service.IDataDictionaryService;
import com.smartindustry.basic.vo.ProcessVO;
import com.smartindustry.basic.vo.PropertyVO;
import com.smartindustry.common.mapper.dd.*;
import com.smartindustry.common.mapper.si.*;
import com.smartindustry.common.pojo.dd.*;
import com.smartindustry.common.pojo.si.*;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private WarehouseMapper warehouseMapper;
    @Autowired
    private WarehouseTypeMapper warehouseTypeMapper;

    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private LocationTypeMapper locationTypeMapper;

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

    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private MaterialTypeMapper materialTypeMapper;
    @Autowired
    private HumidityLevelMapper humidityLevelMapper;
    @Autowired
    private MaterialLevelMapper materialLevelMapper;
    @Autowired
    private MeasureUnitMapper measureUnitMapper;
    @Autowired
    private MaterialVersionMapper materialVersionMapper;
    @Autowired
    private ProduceLossLevelMapper produceLossLevelMapper;
    @Autowired
    private LifeCycleStateMapper lifeCycleStateMapper;
    @Autowired
    private MaterialPropertyMapper materialPropertyMapper;
    @Autowired
    private ProcessMapper processMapper;
    @Autowired
    private MaterialLockMapper materialLockMapper;
    @Autowired
    private StorageLabelMapper storageLabelMapper;
    @Autowired
    private ConfigMapper configMapper;


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
    public ResultVO cQuery() {
        List<Map<String, Object>> res = currencyMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO cEdit(CurrencyDTO dto) {
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
    public ResultVO cDelete(BasicDataDTO dto) {
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

    @Override
    public ResultVO mtQuery() {
        List<Map<String, Object>> res = materialTypeMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO mtEdit(MaterialTypeDTO dto) {
        MaterialTypePO exitPO = materialTypeMapper.queryByName(dto.getMtname());
        if (null != exitPO && (dto.getMtid() == null || !dto.getMtid().equals(exitPO.getMaterialTypeId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getMtid()) {
            // 新增
            materialTypeMapper.insert(MaterialTypeDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        MaterialTypePO materialTypePO = materialTypeMapper.selectByPrimaryKey(dto.getMtid());
        if (null == materialTypePO) {
            return new ResultVO(1002);
        }

        materialTypeMapper.updateByPrimaryKey(MaterialTypeDTO.buildPO(materialTypePO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO mtDelete(BasicDataDTO dto) {
        MaterialTypePO materialTypePO = materialTypeMapper.selectByPrimaryKey(dto.getMtid());
        if (null == materialTypePO) {
            return new ResultVO(1002);
        }

        List<MaterialPO> materialPOs = materialMapper.queryByMtid(dto.getMtid());
        if (null != materialPOs && materialPOs.size() > 0) {
            return new ResultVO(1007);
        }

        materialTypeMapper.deleteByPrimaryKey(dto.getMtid());

        return ResultVO.ok();
    }

    @Override
    public ResultVO hlQuery() {
        List<Map<String, Object>> res = humidityLevelMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO hlEdit(HumidityLevelDTO dto) {
        HumidityLevelPO exitPO = humidityLevelMapper.queryByName(dto.getHlname());
        if (null != exitPO && (dto.getHlid() == null || !dto.getHlid().equals(exitPO.getHumidityLevelId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getHlid()) {
            // 新增
            humidityLevelMapper.insert(HumidityLevelDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        HumidityLevelPO humidityLevelPO = humidityLevelMapper.selectByPrimaryKey(dto.getHlid());
        if (null == humidityLevelPO) {
            return new ResultVO(1002);
        }

        humidityLevelMapper.updateByPrimaryKey(HumidityLevelDTO.buildPO(humidityLevelPO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO hlDelete(BasicDataDTO dto) {
        HumidityLevelPO humidityLevelPO = humidityLevelMapper.selectByPrimaryKey(dto.getHlid());
        if (null == humidityLevelPO) {
            return new ResultVO(1002);
        }

        List<MaterialPO> materialPOs = materialMapper.queryByHlid(dto.getHlid());
        if (null != materialPOs && materialPOs.size() > 0) {
            return new ResultVO(1007);
        }

        humidityLevelMapper.deleteByPrimaryKey(dto.getHlid());

        return ResultVO.ok();
    }

    @Override
    public ResultVO mlQuery() {
        List<Map<String, Object>> res = materialLevelMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO mlEdit(MaterialLevelDTO dto) {
        MaterialLevelPO exitPO = materialLevelMapper.queryByName(dto.getMlname());
        if (null != exitPO && (dto.getMlid() == null || !dto.getMlid().equals(exitPO.getMaterialLevelId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getMlid()) {
            // 新增
            materialLevelMapper.insert(MaterialLevelDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        MaterialLevelPO materialLevelPO = materialLevelMapper.selectByPrimaryKey(dto.getMlid());
        if (null == materialLevelPO) {
            return new ResultVO(1002);
        }

        materialLevelMapper.updateByPrimaryKey(MaterialLevelDTO.buildPO(materialLevelPO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO mlDelete(BasicDataDTO dto) {
        MaterialLevelPO materialLevelPO = materialLevelMapper.selectByPrimaryKey(dto.getMlid());
        if (null == materialLevelPO) {
            return new ResultVO(1002);
        }

        List<MaterialPO> materialPOs = materialMapper.queryByMlid(dto.getMlid());
        if (null != materialPOs && materialPOs.size() > 0) {
            return new ResultVO(1007);
        }

        materialLevelMapper.deleteByPrimaryKey(dto.getMlid());

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
    public ResultVO mvQuery() {
        List<Map<String, Object>> res = materialVersionMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO mvEdit(MaterialVersionDTO dto) {
        MaterialVersionPO exitPO = materialVersionMapper.queryByName(dto.getMvname());
        if (null != exitPO && (dto.getMvid() == null || !dto.getMvid().equals(exitPO.getMaterialVersionId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getMvid()) {
            // 新增
            materialVersionMapper.insert(MaterialVersionDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        MaterialVersionPO materialVersionPO = materialVersionMapper.selectByPrimaryKey(dto.getMvid());
        if (null == materialVersionPO) {
            return new ResultVO(1002);
        }

        materialVersionMapper.updateByPrimaryKey(MaterialVersionDTO.buildPO(materialVersionPO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO mvDelete(BasicDataDTO dto) {
        MaterialVersionPO materialVersionPO = materialVersionMapper.selectByPrimaryKey(dto.getMvid());
        if (null == materialVersionPO) {
            return new ResultVO(1002);
        }

        List<MaterialPO> materialPOs = materialMapper.queryByMvid(dto.getMvid());
        if (null != materialPOs && materialPOs.size() > 0) {
            return new ResultVO(1007);
        }

        materialVersionMapper.deleteByPrimaryKey(dto.getMvid());

        return ResultVO.ok();
    }

    @Override
    public ResultVO pllQuery() {
        List<Map<String, Object>> res = produceLossLevelMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO pllEdit(ProduceLossLevelDTO dto) {
        ProduceLossLevelPO exitPO = produceLossLevelMapper.queryByName(dto.getPllname());
        if (null != exitPO && (dto.getPllid() == null || !dto.getPllid().equals(exitPO.getProduceLossLevelId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getPllid()) {
            // 新增
            produceLossLevelMapper.insert(ProduceLossLevelDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        ProduceLossLevelPO produceLossLevelPO = produceLossLevelMapper.selectByPrimaryKey(dto.getPllid());
        if (null == produceLossLevelPO) {
            return new ResultVO(1002);
        }

        produceLossLevelMapper.updateByPrimaryKey(ProduceLossLevelDTO.buildPO(produceLossLevelPO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO pllDelete(BasicDataDTO dto) {
        ProduceLossLevelPO produceLossLevelPO = produceLossLevelMapper.selectByPrimaryKey(dto.getPllid());
        if (null == produceLossLevelPO) {
            return new ResultVO(1002);
        }

        List<MaterialPO> materialPOs = materialMapper.queryByPllid(dto.getPllid());
        if (null != materialPOs && materialPOs.size() > 0) {
            return new ResultVO(1007);
        }

        produceLossLevelMapper.deleteByPrimaryKey(dto.getPllid());

        return ResultVO.ok();
    }

    @Override
    public ResultVO lcsQuery() {
        List<Map<String, Object>> res = lifeCycleStateMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO lcsEdit(LifeCycleStateDTO dto) {
        LifeCycleStatePO exitPO = lifeCycleStateMapper.queryByName(dto.getLcsname());
        if (null != exitPO && (dto.getLcsid() == null || !dto.getLcsid().equals(exitPO.getLifeCycleStateId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getLcsid()) {
            // 新增
            lifeCycleStateMapper.insert(LifeCycleStateDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        LifeCycleStatePO currencyPO = lifeCycleStateMapper.selectByPrimaryKey(dto.getLcsid());
        if (null == currencyPO) {
            return new ResultVO(1002);
        }

        lifeCycleStateMapper.updateByPrimaryKey(LifeCycleStateDTO.buildPO(currencyPO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO lcsDelete(BasicDataDTO dto) {
        LifeCycleStatePO lifeCycleStatePO = lifeCycleStateMapper.selectByPrimaryKey(dto.getLcsid());
        if (null == lifeCycleStatePO) {
            return new ResultVO(1002);
        }

        List<MaterialPO> materialPOs = materialMapper.queryByLcsid(dto.getLcsid());
        if (null != materialPOs && materialPOs.size() > 0) {
            return new ResultVO(1007);
        }

        lifeCycleStateMapper.deleteByPrimaryKey(dto.getLcsid());

        return ResultVO.ok();
    }

    /**
     * 查询物料属性列表
     * @return
     */
    @Override
    public ResultVO mpQuery(MaterialPropertyDTO dto){
        List<MaterialPropertyPO> pos = materialPropertyMapper.selectAll(dto.getMpname());
        return ResultVO.ok().setData(PropertyVO.convert(pos));
    }

    /**
     *查询物料工序列表
     */
    @Override
    public ResultVO prQuery(ProcessDTO dto){
        List<ProcessPO> pos = processMapper.selectAll(dto.getPrname());
        return ResultVO.ok().setData(ProcessVO.convert(pos));
    }


    /**
     *新增物料属性
     */
    @Override
    public ResultVO mpInsert(MaterialPropertyDTO dto){
        //判断新增物料属性名称是否存在
        MaterialPropertyPO po = materialPropertyMapper.isExist(dto.getMpname());
        if(po != null) {
            return new ResultVO(1004);
        }
        MaterialPropertyPO po1 = MaterialPropertyDTO.createPO(dto);
        materialPropertyMapper.insert(po1);
        return ResultVO.ok();
    }

    /**
     *新增物料工序
     */
    @Override
    public ResultVO prInsert(ProcessDTO dto) {
        ProcessPO po = processMapper.isExist(dto.getPrname());
        if (po != null) {
            //物料工序名称已被使用
            return new ResultVO(1004);
        }
        ProcessPO po1 = ProcessDTO.createPO(dto);
        processMapper.insert(po1);
        return ResultVO.ok();
    }

    @Override
    public ResultVO mlkQuery() {
        List<Map<String, Object>> res = materialLockMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO mlkEdit(MaterialLockDTO dto) {
        MaterialLockPO exitPO = materialLockMapper.queryByName(dto.getMlkname());
        if (null != exitPO && (dto.getMlkid() == null || !dto.getMlkid().equals(exitPO.getMaterialLockId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getMlkid()) {
            // 新增
            materialLockMapper.insert(MaterialLockDTO.createPO(dto, 1L));
            return ResultVO.ok();
        }
        // 修改
        MaterialLockPO materialLockPO = materialLockMapper.selectByPrimaryKey(dto.getMlkid());
        if (null == materialLockPO) {
            return new ResultVO(1002);
        }

        materialLockMapper.updateByPrimaryKey(MaterialLockDTO.buildPO(materialLockPO, dto));
        return ResultVO.ok();
    }

    @Override
    public ResultVO mlkDelete(BasicDataDTO dto) {
        MaterialLockPO materialLockPO = materialLockMapper.selectByPrimaryKey(dto.getMlkid());
        if (null == materialLockPO) {
            return new ResultVO(1002);
        }

        List<StorageLabelPO> storageLabelPOs = storageLabelMapper.queryByMlid(dto.getMlkid());
        if (null != storageLabelPOs && storageLabelPOs.size() > 0) {
            return new ResultVO(1007);
        }

        materialLockMapper.deleteByPrimaryKey(dto.getMlkid());

        return ResultVO.ok();
    }


    @Override
    public ResultVO configSet(ConfigDTO dto){
        ConfigPO po = ConfigDTO.buildPO(dto);
        configMapper.updateConfig(po);
        return ResultVO.ok();
    }

    @Override
    public ResultVO configQuery(){
        List<ConfigPO> pos = configMapper.queryAll();
        return ResultVO.ok().setData(pos);
    }
}
