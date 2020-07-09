package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.StorageLocationPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * StorageLocationMapper继承基类
 */
@Mapper
public interface StorageLocationMapper extends BaseMapper<StorageLocationPO, String> {
}