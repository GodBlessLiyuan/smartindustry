package com.smartindustry.common.mapper.bd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.bd.OrderGoodsRatePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * OrderGoodsRateMapper继承基类
 */
@Mapper
public interface OrderGoodsRateMapper extends BaseMapper<OrderGoodsRatePO, Long> {
}