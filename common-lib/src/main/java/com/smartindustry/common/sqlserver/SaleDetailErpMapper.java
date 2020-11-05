package com.smartindustry.common.sqlserver;

import com.smartindustry.common.pojo.ds.sqlserver.SaleDetailErpPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SaleDetailErpMapper {
    List<SaleDetailErpPO> queryAll();
}