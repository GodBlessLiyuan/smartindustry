package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.LifeCycleStatePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * LifeCycleStateMapper继承基类
 */
@Mapper
public interface LifeCycleStateMapper extends BaseMapper<LifeCycleStatePO, Long> {
    List<Map<String, Object>> queryAll();

    LifeCycleStatePO queryByName(String lcsname);
}