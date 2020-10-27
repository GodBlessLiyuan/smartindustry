package com.smartindustry.common.mapper.om;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.OutboundRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * OutboundRecordMapper继承基类
 */
@Mapper
public interface OutboundRecordMapper extends BaseMapper<OutboundRecordPO, Long> {
}