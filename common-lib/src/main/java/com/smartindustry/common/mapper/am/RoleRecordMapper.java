package com.smartindustry.common.mapper.am;

import com.smartindustry.common.bo.am.DeptRecordBO;
import com.smartindustry.common.bo.am.RoleRecordBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.RoleRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * RoleRecordMapper继承基类
 */

@Mapper
public interface RoleRecordMapper extends BaseMapper<RoleRecordPO, Long> {
    /**
     * 根据操作人查询操作记录
     * @param reqData
     * @return
     */
    List<RoleRecordBO> queryRoleRecord(Map<String, Object> reqData);
}