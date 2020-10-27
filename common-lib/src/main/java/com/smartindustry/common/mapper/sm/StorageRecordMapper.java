package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.StorageRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * StorageRecordMapper继承基类
 */
@Mapper
public interface StorageRecordMapper extends BaseMapper<StorageRecordPO, Long> {
}