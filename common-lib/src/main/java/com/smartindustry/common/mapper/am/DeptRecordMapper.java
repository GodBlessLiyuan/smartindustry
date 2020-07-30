package com.smartindustry.common.mapper.am;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.DeptRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * DeptRecordMapper继承基类
 */

@Mapper
public interface DeptRecordMapper extends BaseMapper<DeptRecordPO, Long> {
}