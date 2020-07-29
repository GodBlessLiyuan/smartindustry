package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.LocationRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * LocationRecordMapper继承基类
 */
@Mapper
public interface LocationRecordMapper extends BaseMapper<LocationRecordPO, Long> {
}