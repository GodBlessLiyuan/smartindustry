package com.smartindustry.common.mapper.ds;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.ds.SalesOutboundDetailPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * SalesOutboundDetailMapper继承基类
 */
@Mapper
public interface SalesOutboundDetailMapper extends BaseMapper<SalesOutboundDetailPO, Long> {
}