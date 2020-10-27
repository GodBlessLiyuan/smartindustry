package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.BomRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * BomRecordMapper继承基类
 */
@Mapper
public interface BomRecordMapper extends BaseMapper<BomRecordPO, Long> {
}