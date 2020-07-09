package com.smartindustry.common.mapper.ba;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.ba.AuthorityPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * AuthorityMapper继承基类
 */
@Mapper
public interface AuthorityMapper extends BaseMapper<AuthorityPO, Long> {
}