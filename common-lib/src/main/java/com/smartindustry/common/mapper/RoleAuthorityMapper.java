package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.RoleAuthorityPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * RoleAuthorityMapper继承基类
 */
@Mapper
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthorityPO, Long> {
}