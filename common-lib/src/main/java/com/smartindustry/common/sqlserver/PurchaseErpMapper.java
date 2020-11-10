package com.smartindustry.common.sqlserver;

import com.smartindustry.common.bo.ds.PurchaseErpBO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseErpMapper {
    List<PurchaseErpBO> queryAll();
}