package com.smartindustry.common.mapper.bd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.bd.OrderGoodsFundsPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * OrderGoodsFundsMapper继承基类
 */
@Mapper
public interface OrderGoodsFundsMapper extends BaseMapper<OrderGoodsFundsPO, Long> {
}