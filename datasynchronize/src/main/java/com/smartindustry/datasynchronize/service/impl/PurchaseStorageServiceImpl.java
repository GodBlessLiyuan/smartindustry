package com.smartindustry.datasynchronize.service.impl;

import com.smartindustry.common.bo.ds.PurchaseErpBO;
import com.smartindustry.common.bo.ds.SaleOutboundErpBO;
import com.smartindustry.common.mapper.sm.StorageBodyMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.ds.SalesOutboundDetailPO;
import com.smartindustry.common.pojo.ds.SalesOutboundPO;
import com.smartindustry.common.pojo.ds.sqlserver.PurchaseDetailErpPO;
import com.smartindustry.common.pojo.ds.sqlserver.SaleDetailErpPO;
import com.smartindustry.common.pojo.sm.StorageBodyPO;
import com.smartindustry.common.pojo.sm.StorageHeadPO;
import com.smartindustry.common.sqlserver.PurchaseErpMapper;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.datasynchronize.service.IPurchaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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


    @Override
    public ResultVO sync() {
        List<PurchaseErpBO> bos = purchaseErpMapper.queryAll();
        List<StorageBodyPO> pos = new ArrayList<>();
        for (PurchaseErpBO bo: bos) {
            StorageHeadPO po = new StorageHeadPO();
            po.setStorageNo(bo.getPurchaseNo());
            po.setSourceType((byte)1);
            po.setSourceNo(bo.getOrderNo());
            po.setStorageTime(bo.getAcceptDate());
            if (bo.getNum() != null) {
                po.setExpectNum(BigDecimal.valueOf(bo.getNum()));
            }
            po.setExtra(bo.getRemark());
            po.setStatus((byte)3);
            po.setCreateTime(Calendar.getInstance().getTime());
            po.setDr((byte)1);
            storageHeadMapper.insert(po);
            if (bo.getPdpos() != null && !bo.getPdpos().isEmpty()) {
                pos.addAll( convert(bo.getPdpos(), po.getStorageHeadId()));
            }

        }
        if (!pos.isEmpty()) {
            storageBodyMapper.batchInsert(pos);
        }
        return ResultVO.ok().setData(bos);
    }

    private List<StorageBodyPO> convert(List<PurchaseDetailErpPO> sdpos, Long storageHeadId) {
        List<StorageBodyPO> pos = new ArrayList<>(sdpos.size());
        for (PurchaseDetailErpPO ep: sdpos) {
            StorageBodyPO po = new StorageBodyPO();
            pos.add(po);
        }
        return pos;
    }
}
