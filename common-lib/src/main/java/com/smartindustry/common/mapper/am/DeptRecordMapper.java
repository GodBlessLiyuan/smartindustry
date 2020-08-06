package com.smartindustry.common.mapper.am;

import com.smartindustry.common.bo.am.DeptRecordBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.DeptRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * DeptRecordMapper继承基类
 */

@Mapper
public interface DeptRecordMapper extends BaseMapper<DeptRecordPO, Long> {

    /**
     * 根据操作人查询操作记录
     * @param reqData
     * @return
     */
    List<DeptRecordBO> queryDeptRecord(Map<String, Object> reqData);
}