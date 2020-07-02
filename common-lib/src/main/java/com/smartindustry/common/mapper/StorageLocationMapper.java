package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.StorageLocationPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * StorageLocationMapper继承基类
 */
@Mapper
public interface StorageLocationMapper extends BaseMapper<StorageLocationPO, String> {
}