package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.MaterialSpecificationPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MaterialSpecificationMapper继承基类
 */
@Mapper
public interface MaterialSpecificationMapper extends BaseMapper<MaterialSpecificationPO, Long> {
}