package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.MaterialPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MaterialMapper继承基类
 */
@Mapper
public interface MaterialMapper extends BaseMapper<MaterialPO, String> {
}