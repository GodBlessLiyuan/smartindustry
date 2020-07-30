package com.smartindustry.common.mapper.am;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.UserRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserRecordMapper继承基类
 */

@Mapper
public interface UserRecordMapper extends BaseMapper<UserRecordPO, Long> {
}