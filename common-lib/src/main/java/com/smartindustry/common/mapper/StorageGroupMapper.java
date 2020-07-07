package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.StorageGroupPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * StorageGroupMapper继承基类
 */
@Mapper
public interface StorageGroupMapper extends BaseMapper<StorageGroupPO, Long> {
}