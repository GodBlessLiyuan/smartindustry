package com.smartindustry.common.mapper.am;

import com.smartindustry.common.bo.am.RoleRecordBO;
import com.smartindustry.common.bo.am.UserRecordBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.UserRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * UserRecordMapper继承基类
 */

@Mapper
public interface UserRecordMapper extends BaseMapper<UserRecordPO, Long> {
    /**
     * 根据操作人查询用户操作记录
     * @param reqData
     * @return
     */
    List<UserRecordBO> queryUserRecord(Map<String, Object> reqData);
}