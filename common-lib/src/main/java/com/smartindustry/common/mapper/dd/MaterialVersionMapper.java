package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.MaterialVersionPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MaterialVersionMapper继承基类
 */
@Mapper
public interface MaterialVersionMapper extends BaseMapper<MaterialVersionPO, Long> {
}