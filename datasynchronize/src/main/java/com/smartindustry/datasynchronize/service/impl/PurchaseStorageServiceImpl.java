package com.smartindustry.datasynchronize.service.impl;

import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.si.SupplierMapper;
import com.smartindustry.common.mapper.sm.StorageBodyMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.ds.sqlserver.PurchaseDetailErpPO;
import com.smartindustry.common.pojo.ds.sqlserver.PurchaseErpPO;
import com.smartindustry.common.pojo.sm.StorageBodyPO;
import com.smartindustry.common.sqlserver.PurchaseErpMapper;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.datasynchronize.constant.SyncConstant;
import com.smartindustry.datasynchronize.service.IPurchaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
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
         }
        return ResultVO.ok();
    }

    private List<StorageBodyPO> convert(List<PurchaseDetailErpPO> sdpos, Date acceptDate, Long supplierId) {
        return null;
    }
}
