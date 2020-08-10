package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.MeasureUnitPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * MeasureUnitMapper继承基类
 */
@Mapper
public interface MeasureUnitMapper extends BaseMapper<MeasureUnitPO, Long> {
    List<Map<String, Object>> queryAll();

    MeasureUnitPO queryByName(String muname);
}