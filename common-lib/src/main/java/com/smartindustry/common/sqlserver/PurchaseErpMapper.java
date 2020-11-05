package com.smartindustry.common.sqlserver;

import com.smartindustry.common.pojo.ds.sqlserver.PurchaseErpPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseErpMapper {
    List<PurchaseErpPO> queryAll();
}