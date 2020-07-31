package com.smartindustry.common.mapper.am;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.RolePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RoleMapper继承基类
 */

@Mapper
public interface RoleMapper extends BaseMapper<RolePO, Long> {

    List<RolePO> selectAll(@Param("roleName") String roleName);
}