package com.smartindustry.common.mapper.ba;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.ba.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserMapper继承基类
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO, Long> {
}