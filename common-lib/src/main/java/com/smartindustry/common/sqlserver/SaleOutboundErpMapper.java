package com.smartindustry.common.sqlserver;

import com.smartindustry.common.pojo.ds.sqlserver.SaleOutboundErpPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SaleOutboundErpMapper {
    List<SaleOutboundErpPO> queryAll();
}