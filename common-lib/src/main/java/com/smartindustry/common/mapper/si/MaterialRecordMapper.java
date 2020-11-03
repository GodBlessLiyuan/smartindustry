package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.MaterialRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MaterialRecordMapper继承基类
 */
@Mapper
public interface MaterialRecordMapper extends BaseMapper<MaterialRecordPO, Long> {
}