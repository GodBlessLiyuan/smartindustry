package com.smartindustry.common.sqlserver;

import com.smartindustry.common.pojo.ds.sqlserver.PurchaseDetailErpPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseDetailErpMapper {
    List<PurchaseDetailErpPO> queryAll();

    void batchInsert(List<PurchaseDetailErpPO> erppos);
}