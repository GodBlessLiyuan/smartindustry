package com.smartindustry.common.mapper.am;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.RoleRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * RoleRecordMapper继承基类
 */

@Mapper
public interface RoleRecordMapper extends BaseMapper<RoleRecordPO, Long> {
}