package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.HumidityLevelPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * HumidityLevelMapper继承基类
 */
@Mapper
public interface HumidityLevelMapper extends BaseMapper<HumidityLevelPO, Long> {
}