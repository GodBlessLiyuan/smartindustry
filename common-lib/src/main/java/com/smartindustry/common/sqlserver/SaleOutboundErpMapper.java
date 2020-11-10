package com.smartindustry.common.sqlserver;

import com.smartindustry.common.bo.ds.SaleOutboundErpBO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SaleOutboundErpMapper {
    List<SaleOutboundErpBO> queryAll();
}