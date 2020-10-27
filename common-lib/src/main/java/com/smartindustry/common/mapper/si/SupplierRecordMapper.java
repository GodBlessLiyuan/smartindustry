package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.SupplierRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * SupplierRecordMapper继承基类
 */
@Mapper
public interface SupplierRecordMapper extends BaseMapper<SupplierRecordPO, Long> {
}