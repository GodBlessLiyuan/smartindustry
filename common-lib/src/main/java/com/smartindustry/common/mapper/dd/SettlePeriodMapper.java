package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.SettlePeriodPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * SettlePeriodMapper继承基类
 */
@Mapper
public interface SettlePeriodMapper extends BaseMapper<SettlePeriodPO, Long> {
    List<Map<String, Object>> queryAll();

    SettlePeriodPO queryByName(String spname);
}