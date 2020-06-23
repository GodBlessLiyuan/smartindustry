package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.RolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * RoleMapper继承基类
 */
@Mapper
public interface RoleMapper extends BaseMapper<RolePO, Long> {
}