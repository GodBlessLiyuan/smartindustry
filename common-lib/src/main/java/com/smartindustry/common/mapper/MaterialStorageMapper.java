package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.MaterialStoragePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MaterialStorageMapper继承基类
 */
@Mapper
public interface MaterialStorageMapper extends BaseMapper<MaterialStoragePO, Long> {
}