package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.UserAuthorityPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserAuthorityMapper继承基类
 */
@Mapper
public interface UserAuthorityMapper extends BaseMapper<UserAuthorityPO, Long> {
}