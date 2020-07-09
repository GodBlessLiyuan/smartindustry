package com.smartindustry.common.mapper.ba;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.ba.UserRolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserRoleMapper继承基类
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRolePO, Long> {
}