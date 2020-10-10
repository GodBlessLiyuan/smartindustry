package com.smartindustry.common.mapper.bd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.bd.FirstGoodsFundsPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * FirstGoodsFundsMapper继承基类
 */
@Mapper
public interface FirstGoodsFundsMapper extends BaseMapper<FirstGoodsFundsPO, Long> {
}