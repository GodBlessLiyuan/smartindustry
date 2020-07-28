package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.SupplierTypePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * SupplierTypeMapper继承基类
 */
@Mapper
public interface SupplierTypeMapper extends BaseMapper<SupplierTypePO, Long> {
}