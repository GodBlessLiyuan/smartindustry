package com.smartindustry.datasynchronize.service.impl;

import com.smartindustry.common.bo.sm.StorageBodyBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.si.SupplierMapper;
import com.smartindustry.common.mapper.sm.StorageBodyMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.ds.sqlserver.MaterialErpPO;
import com.smartindustry.common.pojo.ds.sqlserver.PurchaseDetailErpPO;
import com.smartindustry.common.pojo.ds.sqlserver.PurchaseErpPO;
import com.smartindustry.common.pojo.ds.sqlserver.SupplierErpPO;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.si.SupplierPO;
import com.smartindustry.common.pojo.sm.StorageBodyPO;
import com.smartindustry.common.sqlserver.MaterialErpMapper;
import com.smartindustry.common.sqlserver.PurchaseDetailErpMapper;
import com.smartindustry.common.sqlserver.PurchaseErpMapper;
import com.smartindustry.common.sqlserver.SupplierErpMapper;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.datasynchronize.constant.SyncConstant;
import com.smartindustry.datasynchronize.service.IPurchaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

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
    private PurchaseDetailErpMapper purchaseDetailErpMapper;

    @Autowired
    private StorageHeadMapper storageHeadMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private SupplierErpMapper supplierErpMapper;

    @Autowired
    private MaterialErpMapper materialErpMapper;


    /**
     * 原材料新增入库 同步
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO sync() {
        Map<String, Object>reqData = new HashMap<>();
         List<StorageHeadBO> bos = storageHeadMapper.pageQuery(reqData);
         List<PurchaseErpPO> pos = new ArrayList<>(bos.size());
         Map<String, List<PurchaseDetailErpPO>> map = new HashMap<>(bos.size());
         for (StorageHeadBO bo : bos) {
             PurchaseErpPO po = new PurchaseErpPO();
             po.setPurchaseNo(bo.getStorageNo());
             po.setRemark(bo.getExtra());
             po.setPayMethod(SyncConstant.PAY_METHOD_DEBT.equals(bo.getPayMethod())?SyncConstant.PAY_METHOD_ERP_DEBT: SyncConstant.PAY_METHOD_ERP_BILL);
             po.setPayName(SyncConstant.PAY_METHOD_DEBT.equals(bo.getPayMethod())?SyncConstant.PAY_NAME_ERP_DEBT: SyncConstant.PAY_NAME_ERP_BILL);
             po.setAcceptDate(bo.getStorageTime());
             if (bo.getBos() != null && bo.getBos().size()>0) {
                 List<PurchaseDetailErpPO> epos = convert(bo.getBos(), po.getAcceptDate());
                 map.put(po.getPurchaseNo(),epos );
                 po.setNum(epos.stream().mapToDouble(PurchaseDetailErpPO::getNum).sum());
                 if (!epos.isEmpty()) {
                     po.setSupplierId(epos.get(0).getSupplierId());
                     po.setSupplierNo(epos.get(0).getSupplierNo());
                 }
             }
             pos.add(po);
         }
         if (pos.isEmpty()) {
             purchaseErpMapper.batchInsert(pos);
         }
         List<PurchaseDetailErpPO> erppos = new ArrayList<>();
         for (PurchaseErpPO po: pos) {
//             purchaseErpMapper.insert(po);
             List<PurchaseDetailErpPO > dpos = map.get(po.getPurchaseNo());
             if (dpos != null && dpos.size()> 0) {
                 for (PurchaseDetailErpPO dpo: dpos) {
                     dpo.setPurchaseId(po.getPurchaseId());
                     erppos.add(dpo);
                 }
             }
         }
         if (!erppos.isEmpty()) {
//             for (PurchaseDetailErpPO po: erppos) {
//                 purchaseDetailErpMapper.insert(po);
//             }
            purchaseDetailErpMapper.batchInsert(erppos);
         }
        return ResultVO.ok();
    }

    private List<PurchaseDetailErpPO> convert(List<StorageBodyBO> pos, Date acceptDate) {
        List<PurchaseDetailErpPO> erppos = new ArrayList<>(pos.size());
        for (StorageBodyBO bo: pos) {
            PurchaseDetailErpPO p = convertSingle(bo, acceptDate);
            if (p != null) {
                erppos.add(p);
            }
        }
        return erppos;
    }

    private PurchaseDetailErpPO convertSingle(StorageBodyBO po, Date acceptDate) {
        PurchaseDetailErpPO erppo = new PurchaseDetailErpPO();
        MaterialPO mpo = materialMapper.selectByPrimaryKey(po.getMaterialId());
        if (mpo == null) {
            return null;
        }
        erppo.setNum(po.getStorageNum().doubleValue());
        erppo.setUnitPrice(po.getUnitPrice().doubleValue());
        erppo.setUnitPriceNoTax(po.getUnitPriceNotax().doubleValue());
        erppo.setSum(po.getSumPrice().doubleValue());
        erppo.setSumNoTax(po.getSumPriceNotax().doubleValue());
        erppo.setMaterialName(mpo.getMaterialName());
        erppo.setMaterialNo(mpo.getMaterialNo());
        MaterialErpPO erpmpo = materialErpMapper.queryByNo(po.getMaterialNo(), po.getMaterialName());
        if (erpmpo == null) {
            return null;
        }
        erppo.setMaterialId(erpmpo.getMaterialId());
        //查找
        SupplierPO supplier  = supplierMapper.selectByPrimaryKey(mpo.getSupplierId());
        SupplierErpPO erpspo = supplierErpMapper.queryByNo(supplier.getSupplierNo());
        if (supplier == null) {
            return null;
        }
        erppo.setSupplierId(erpspo.getSupplierId());
        erppo.setSupplierNo(erpspo.getSupplierCode());
        return erppo;
    }
}
