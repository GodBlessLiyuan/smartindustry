package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.MaterialLevelPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MaterialLevelMapper继承基类
 */
@Mapper
public interface MaterialLevelMapper extends BaseMapper<MaterialLevelPO, Long> {
}