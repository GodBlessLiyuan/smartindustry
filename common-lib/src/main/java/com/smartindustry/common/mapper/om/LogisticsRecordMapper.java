package com.smartindustry.common.mapper.om;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.LogisticsRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * LogisticsRecordMapper继承基类
 */
@Mapper
public interface LogisticsRecordMapper extends BaseMapper<LogisticsRecordPO, Long> {
}