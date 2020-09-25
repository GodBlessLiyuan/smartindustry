package com.smartindustry.common.mapper.wm;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.wm.WorkBenchPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * mWorkBenchMapper继承基类
 */
@Mapper
public interface WorkBenchMapper extends BaseMapper<WorkBenchPO, Long> {
}