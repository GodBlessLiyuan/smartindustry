package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.LifeCycleStatePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * LifeCycleStateMapper继承基类
 */
@Mapper
public interface LifeCycleStateMapper extends BaseMapper<LifeCycleStatePO, Long> {
}