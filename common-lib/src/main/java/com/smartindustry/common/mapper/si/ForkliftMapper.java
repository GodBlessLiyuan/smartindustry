package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.ForkliftPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ForkliftMapper继承基类
 */
@Mapper
public interface ForkliftMapper extends BaseMapper<ForkliftPO, Long> {
}