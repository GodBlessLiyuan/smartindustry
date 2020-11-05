package com.smartindustry.common.sqlserver;

import com.smartindustry.common.pojo.ds.sqlserver.SupplierErpPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupplierErpMapper {
    List<SupplierErpPO> queryAll();
}