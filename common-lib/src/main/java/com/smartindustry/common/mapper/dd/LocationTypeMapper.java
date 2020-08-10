package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.LocationTypePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * LocationTypeMapper继承基类
 */
@Mapper
public interface LocationTypeMapper extends BaseMapper<LocationTypePO, Long> {
    List<Map<String, Object>> queryAll();

    LocationTypePO queryByName(String ltname);
}