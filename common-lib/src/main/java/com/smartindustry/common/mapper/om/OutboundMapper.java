package com.smartindustry.common.mapper.om;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.OutboundPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * OutboundMapper继承基类
 */
@Mapper
public interface OutboundMapper extends BaseMapper<OutboundPO, Long> {
}