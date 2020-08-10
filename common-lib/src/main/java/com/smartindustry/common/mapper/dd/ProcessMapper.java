package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.ProcessPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ProcessMapper继承基类
 */
@Mapper
public interface ProcessMapper extends BaseMapper<ProcessPO, Long> {
}