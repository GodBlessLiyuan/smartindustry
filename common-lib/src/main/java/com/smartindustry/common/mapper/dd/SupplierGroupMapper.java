package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.SupplierGroupPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * SupplierGroupMapper继承基类
 */
@Mapper
public interface SupplierGroupMapper extends BaseMapper<SupplierGroupPO, Long> {
}