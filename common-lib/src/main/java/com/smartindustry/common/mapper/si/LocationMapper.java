package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.LocationPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * LocationMapper继承基类
 */
@Mapper
public interface LocationMapper extends BaseMapper<LocationPO, String> {
}