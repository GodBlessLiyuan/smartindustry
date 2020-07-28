package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.CurrencyPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * CurrencyMapper继承基类
 */
@Mapper
public interface CurrencyMapper extends BaseMapper<CurrencyPO, Long> {
}