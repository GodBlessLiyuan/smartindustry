package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.MetarialStoragePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MetarialStorageMapper继承基类
 */
@Mapper
public interface MetarialStorageMapper extends BaseMapper<MetarialStoragePO, Long> {
}