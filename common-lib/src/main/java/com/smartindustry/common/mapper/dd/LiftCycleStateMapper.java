package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.LiftCycleStatePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * LiftCycleStateMapper继承基类
 */
@Mapper
public interface LiftCycleStateMapper extends BaseMapper<LiftCycleStatePO, Long> {
}