package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.UserRolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserRoleMapper继承基类
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRolePO, Long> {
}