package com.smartindustry.common.mapper.bd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.bd.OrderGoodsNumPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * OrderGoodsNumMapper继承基类
 */
@Mapper
public interface OrderGoodsNumMapper extends BaseMapper<OrderGoodsNumPO, Long> {
}