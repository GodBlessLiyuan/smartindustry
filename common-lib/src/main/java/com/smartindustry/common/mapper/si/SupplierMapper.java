package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.SupplierPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * SupplierMapper继承基类
 */
@Mapper
public interface SupplierMapper extends BaseMapper<SupplierPO, Long> {
}