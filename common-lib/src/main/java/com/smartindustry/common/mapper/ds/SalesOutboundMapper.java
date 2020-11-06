package com.smartindustry.common.mapper.ds;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.ds.SalesOutboundPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * SalesOutboundMapper继承基类
 */
@Mapper
public interface SalesOutboundMapper extends BaseMapper<SalesOutboundPO, Long> {
}