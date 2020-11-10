package com.smartindustry.inventory.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.im.MaterialInventoryBO;
import com.smartindustry.common.mapper.im.MaterialInventoryMapper;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.service.IMaterialInventoryService;
import com.smartindustry.inventory.vo.MaterialInventoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.*;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 16:06
 * @description: 物料库存统计
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class MaterialInventoryServiceImpl implements IMaterialInventoryService {
    @Autowired
    private MaterialInventoryMapper materialInventoryMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<MaterialInventoryBO> page = PageQueryUtil.startPage(reqData);
        List<Long> miids = materialInventoryMapper.pageQuery(reqData);
        List<MaterialInventoryBO> bos = new ArrayList<>();
        if (null != miids && miids.size() > 0) {
            bos = materialInventoryMapper.queryByMiids(miids);
        }
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialInventoryVO.convert(bos)));
    }

//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public ResultVO safeStock(SafeStockDTO dto) {
//        List<SafeStockPO> insPOs = new ArrayList<>();
//        List<SafeStockPO> updPOs = new ArrayList<>();
//        List<Long> mids = new ArrayList<>();
//        for (SafeStockDTO.MaterialInventoryDTO mi : dto.getMi()) {
//            mids.add(mi.getMid());
//
//            SafeStockPO po = new SafeStockPO();
//            po.setMaterialInventoryId(mi.getMiid());
//            po.setLowerLimit(dto.getLlimit());
//            po.setWay(dto.getWay());
//            if (null == mi.getSsid()) {
//                po.setUserId(1L);
//                po.setCreateTime(new Date());
//                insPOs.add(po);
//            } else {
//                po.setSafeStockId(mi.getSsid());
//                updPOs.add(po);
//            }
//        }
//        if (insPOs.size() > 0) {
//            safeStockMapper.batchInsert(insPOs);
//        }
//        if (updPOs.size() > 0) {
//            safeStockMapper.batchUpdate(updPOs);
//        }
//
//        // 物料库存
//        List<MaterialInventoryBO> materialInventoryBOs = materialInventoryMapper.queryByMids(mids);
//        for (MaterialInventoryBO materialInventoryBO : materialInventoryBOs) {
//            materialInventoryMapper.updateByPrimaryKey(materialInventoryBO.updatePO(new MaterialInventoryPO()));
//        }
//
//        // 物料属性
//        List<MaterialAttributePO> attributePOs = materialAttributeMapper.queryByMids(mids);
//        Map<Long, MaterialAttributePO> attributeMap = new HashMap<>();
//        for (MaterialAttributePO attributePO : attributePOs) {
//            attributePO.setWay(dto.getWay());
//            attributePO.setLowerLimit(dto.getLlimit());
//            attributeMap.put(attributePO.getMaterialId(), attributePO);
//        }
//        List<MaterialAttributePO> insAttrPOs = new ArrayList<>();
//        for (Long mid : mids) {
//            if (!attributeMap.containsKey(mid)) {
//                MaterialAttributePO po = new MaterialAttributePO();
//                po.setLowerLimit(dto.getLlimit());
//                po.setWay(dto.getWay());
//                po.setMaterialId(mid);
//                insAttrPOs.add(po);
//            }
//        }
//
//        if (attributePOs.size() > 0) {
//            materialAttributeMapper.batchUpdate(new ArrayList<>(attributeMap.values()));
//        }
//        if (insAttrPOs.size() > 0) {
//            materialAttributeMapper.batchInsert(insAttrPOs);
//        }
//
//        return ResultVO.ok();
//    }
}
