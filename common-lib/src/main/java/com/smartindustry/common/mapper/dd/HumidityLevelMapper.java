package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.HumidityLevelPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * HumidityLevelMapper继承基类
 */
@Mapper
public interface HumidityLevelMapper extends BaseMapper<HumidityLevelPO, Long> {
    List<Map<String, Object>> queryAll();

    HumidityLevelPO queryByName(String hlname);
}