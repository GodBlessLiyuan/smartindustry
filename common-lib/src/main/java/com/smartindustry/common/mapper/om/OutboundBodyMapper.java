package com.smartindustry.common.mapper.om;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * OutboundBodyMapper继承基类
 */
@Mapper
public interface OutboundBodyMapper extends BaseMapper<OutboundBodyPO, Long> {
}