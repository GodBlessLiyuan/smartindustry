package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.MaterialPropertyPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MaterialPropertyMapper继承基类
 */
@Mapper
public interface MaterialPropertyMapper extends BaseMapper<MaterialPropertyPO, Long> {
}