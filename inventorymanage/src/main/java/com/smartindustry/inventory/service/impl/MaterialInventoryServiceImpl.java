package com.smartindustry.inventory.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.si.MaterialInventoryBO;
import com.smartindustry.common.bo.si.ProductDetailBO;
import com.smartindustry.common.bo.si.WarehouseBO;
import com.smartindustry.common.constant.ExceptionEnums;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.si.MaterialInventoryMapper;
import com.smartindustry.common.mapper.si.WarehouseMapper;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.si.MaterialInventoryPO;
import com.smartindustry.common.pojo.si.WarehousePO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.constant.InventoryConstant;
import com.smartindustry.inventory.dto.OperateDTO;
import com.smartindustry.inventory.dto.SafeStockDTO;
import com.smartindustry.inventory.service.IMaterialInventoryService;
import com.smartindustry.inventory.vo.LocationVO;
import com.smartindustry.inventory.vo.MaterialInventoryVO;
import com.smartindustry.inventory.vo.ProductDetailVO;
import com.smartindustry.inventory.vo.WarehouseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: jiangchaojie
 * @date: Created in 2020/11/11
 * @description: 物料库存统计
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class MaterialInventoryServiceImpl implements IMaterialInventoryService {
    @Autowired
    private MaterialInventoryMapper materialInventoryMapper;
    @Autowired
    private WarehouseMapper warehouseMapper;
    @Autowired
    private LocationMapper locationMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<MaterialInventoryBO> page = PageQueryUtil.startPage(reqData);
        List<MaterialInventoryBO> bos = materialInventoryMapper.pageQuery(reqData);
        // 查询得到所有物料的当前库存数量
        Map<BigInteger, Map<Long, BigDecimal>> map = materialInventoryMapper.queryMaterialMap();
        List<MaterialInventoryVO> vos = MaterialInventoryVO.convert(bos,map);
        List<MaterialInventoryVO> pre = vos.stream().filter(p -> p.getStatus()!= null && p.getStatus().equals(InventoryConstant.STATUS_NORMAL)).collect(Collectors.toList());
        List<MaterialInventoryVO> back = vos.stream().filter(p -> p.getStatus()== null || !p.getStatus().equals(InventoryConstant.STATUS_NORMAL)).collect(Collectors.toList());
        pre.addAll(back);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(),pre));
    }

    @Override
    public ResultVO safeStock(SafeStockDTO dto){
        // 设置安全库存前先对物料安全库存进行删除
        materialInventoryMapper.deleteBatch(dto.getMids());
        List<MaterialInventoryPO> pos = SafeStockDTO.createPOS(dto);
        if(pos.isEmpty()){
            return new ResultVO(ExceptionEnums.NOT_EMPTY.getCode());
        }
        // 批量新增
        materialInventoryMapper.batchInsert(pos);
        return ResultVO.ok();
    }

    @Override
    public ResultVO pageQueryPro(Map<String, Object> reqData){
        Page<ProductDetailBO> page = PageQueryUtil.startPage(reqData);
        List<ProductDetailBO> bos = materialInventoryMapper.pageQueryPro(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), ProductDetailVO.convert(bos)));
    }

    @Override
    public ResultVO queryWarehouse(){
        List<WarehousePO> pos = warehouseMapper.queryWarehouse();
        return ResultVO.ok().setData(WarehouseVO.convert(pos));
    }

    @Override
    public ResultVO queryLocation(OperateDTO dto){
        List<LocationPO> pos = locationMapper.queryLocation(dto.getWid());
        return ResultVO.ok().setData(LocationVO.convert(pos));
    }
}
