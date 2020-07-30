package com.smartindustry.common.mapper.am;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.RolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * RoleMapper继承基类
 */

@Mapper
public interface RoleMapper extends BaseMapper<RolePO, Long> {
}