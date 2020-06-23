package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.RecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * RecordMapper继承基类
 */
@Mapper
public interface RecordMapper extends BaseMapper<RecordPO, Long> {
}