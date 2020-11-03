package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.WarehouseRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * WarehouseRecordMapper继承基类
 */
@Mapper
public interface WarehouseRecordMapper extends BaseMapper<WarehouseRecordPO, Long> {
}