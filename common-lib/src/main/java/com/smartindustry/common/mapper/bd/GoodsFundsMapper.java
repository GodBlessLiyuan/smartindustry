package com.smartindustry.common.mapper.bd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.bd.GoodsFundsPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * GoodsFundsMapper继承基类
 */
@Mapper
public interface GoodsFundsMapper extends BaseMapper<GoodsFundsPO, Long> {
}