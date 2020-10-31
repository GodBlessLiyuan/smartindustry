package com.smartindustry.common.mapper.pda;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.pda.StorageDetailPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * StorageDetailMapper继承基类
 */
@Mapper
public interface StorageDetailMapper extends BaseMapper<StorageDetailPO, Long> {
}