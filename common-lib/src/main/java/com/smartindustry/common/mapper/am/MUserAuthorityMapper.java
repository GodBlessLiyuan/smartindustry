package com.smartindustry.common.mapper.am;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.MUserAuthorityPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MUserAuthorityMapper继承基类
 */
@Mapper
public interface MUserAuthorityMapper extends BaseMapper<MUserAuthorityPO, Long> {
}