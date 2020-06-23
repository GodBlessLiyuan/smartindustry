package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.AuthorityPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * AuthorityMapper继承基类
 */
@Mapper
public interface AuthorityMapper extends BaseMapper<AuthorityPO, Long> {
}