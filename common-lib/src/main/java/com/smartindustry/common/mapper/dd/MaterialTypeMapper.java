package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.MaterialTypePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MaterialTypeMapper继承基类
 */
@Mapper
public interface MaterialTypeMapper extends BaseMapper<MaterialTypePO, Long> {
}