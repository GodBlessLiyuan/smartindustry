package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.MaterialPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MaterialMapper继承基类
 */
@Mapper
public interface MaterialMapper extends BaseMapper<MaterialPO, String> {
}