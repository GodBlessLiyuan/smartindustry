package com.smartindustry.common.mapper.ba;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.ba.RoleAuthorityPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * RoleAuthorityMapper继承基类
 */
@Mapper
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthorityPO, Long> {
}