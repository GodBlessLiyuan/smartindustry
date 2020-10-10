package com.smartindustry.common.mapper.bd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.bd.MaWareFundsPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MaWareFundsMapper继承基类
 */
@Mapper
public interface MaWareFundsMapper extends BaseMapper<MaWareFundsPO, Long> {
}