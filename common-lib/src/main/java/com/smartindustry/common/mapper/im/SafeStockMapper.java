package com.smartindustry.common.mapper.im;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.im.SafeStockPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * SafeStockMapper继承基类
 */
@Mapper
public interface SafeStockMapper extends BaseMapper<SafeStockPO, Long> {
}