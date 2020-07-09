package com.smartindustry.common.mapper.ba;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.ba.UserAuthorityPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserAuthorityMapper继承基类
 */
@Mapper
public interface UserAuthorityMapper extends BaseMapper<UserAuthorityPO, Long> {
}