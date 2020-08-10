package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.MaterialUnitPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MaterialUnitMapper继承基类
 */
@Mapper
public interface MaterialUnitMapper extends BaseMapper<MaterialUnitPO, Long> {
}