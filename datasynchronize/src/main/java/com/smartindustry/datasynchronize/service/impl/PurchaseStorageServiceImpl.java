package com.smartindustry.datasynchronize.service.impl;

import com.smartindustry.common.bo.ds.PurchaseErpBO;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.si.SupplierMapper;
import com.smartindustry.common.mapper.sm.StorageBodyMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.ds.sqlserver.PurchaseDetailErpPO;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.si.SupplierPO;
import com.smartindustry.common.pojo.sm.StorageBodyPO;
import com.smartindustry.common.pojo.sm.StorageHeadPO;
import com.smartindustry.common.sqlserver.PurchaseErpMapper;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.datasynchronize.constant.SyncConstant;
import com.smartindustry.datasynchronize.service.IPurchaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author hui.feng
 * @date created in 2020/11/5
 * @description
 */
@Service
@EnableTransactionManagement
public class PurchaseStorageServiceImpl implements IPurchaseStorageService {

    @Autowired
    private PurchaseErpMapper purchaseErpMapper;

    @Autowired
    private StorageHeadMapper storageHeadMapper;

    @Autowired
    private StorageBodyMapper storageBodyMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private MaterialMapper materialMapper;


    /**
     * 原材料新增入库 同步
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO sync() {
        //从erp中获取数据
        List<PurchaseErpBO> bos = purchaseErpMapper.queryAll();
        List<StorageHeadPO> hpos = new ArrayList<>(bos.size());
        Map<String, List<StorageBodyPO>> map = new HashMap<>(hpos.size());
        for (PurchaseErpBO bo: bos) {
            StorageHeadPO po = new StorageHeadPO();
            po.setStorageNo(bo.getPurchaseNo());
            if (storageHeadMapper.queryByStorageNo(po.getStorageNo()) != null) {
                continue;
            }
            po.setSourceType((byte)1);
            po.setSourceNo(bo.getOrderNo());
            po.setStorageTime(bo.getAcceptDate());
            if (bo.getNum() != null) {
                po.setExpectNum(BigDecimal.valueOf(bo.getNum()));
            } else {
                po.setExpectNum(BigDecimal.valueOf(0));
            }
            po.setExtra(bo.getRemark());
            po.setStatus((byte)3);
            po.setCreateTime(Calendar.getInstance().getTime());
            po.setDr((byte)1);
            po.setPayMethod(bo.getPayMethod().equals(SyncConstant.PAY_METHOD_ERP_BILL) ?SyncConstant.PAY_METHOD_BILL:SyncConstant.PAY_METHOD_DEBT);
            SupplierPO supplier = supplierMapper.queryBySno(bo.getSupplierNo());
            if (supplier == null) {
                continue;
            }
            hpos.add(po);
            if (bo.getPdpos() != null && !bo.getPdpos().isEmpty()) {
                map.put(po.getStorageNo(), convert(bo.getPdpos(), po.getStorageTime(), supplier.getSupplierId()));
            }
        }
        if (!hpos.isEmpty()) {
            storageHeadMapper.batchInsert(hpos);
            List<StorageBodyPO> pos = new ArrayList<>(bos.size());
            for (StorageHeadPO po: hpos) {
                List<StorageBodyPO> bpos = map.get(po.getStorageNo());
                for (StorageBodyPO bpo: bpos) {
                    bpo.setStorageHeadId(po.getStorageHeadId());
                    pos.add(bpo);
                }
            }
            if (!pos.isEmpty()) {
                storageBodyMapper.batchInsert(pos);
            }
        }
        return ResultVO.ok();
    }

    private List<StorageBodyPO> convert(List<PurchaseDetailErpPO> sdpos, Date acceptDate, Long supplierId) {
        List<StorageBodyPO> pos = new ArrayList<>(sdpos.size());
        for (PurchaseDetailErpPO ep: sdpos) {
            StorageBodyPO po = new StorageBodyPO();
            po.setSupplierId(supplierId);
            MaterialPO material = materialMapper.queryByMaterialNo(ep.getMaterialNo(), ep.getMaterialName());
            if (material == null){
                continue;
            }
            po.setMaterialId(material.getMaterialId());
            po.setExpectNum(BigDecimal.valueOf(ep.getNum()));
            po.setCreateTime(Calendar.getInstance().getTime());
            po.setDr((byte)1);
            po.setAcceptTime(acceptDate);
            po.setUnitPrice(BigDecimal.valueOf(ep.getUnitPrice()));
            po.setSumPrice(BigDecimal.valueOf(ep.getSum()));
            po.setUnitPriceNotax(BigDecimal.valueOf(ep.getUnitPriceNoTax()));
            po.setSumPriceNotax(BigDecimal.valueOf(ep.getSumNoTax()));
            pos.add(po);
        }
        return pos;
    }
}
