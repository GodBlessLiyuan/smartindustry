package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.StorageDetailPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * StorageDetailMapper继承基类
 */
@Mapper
public interface StorageDetailMapper extends BaseMapper<StorageDetailPO, Long> {
}