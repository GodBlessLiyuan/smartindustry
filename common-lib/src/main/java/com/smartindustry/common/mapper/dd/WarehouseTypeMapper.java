package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.WarehouseTypePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * WarehouseTypeMapper继承基类
 */
@Mapper
public interface WarehouseTypeMapper extends BaseMapper<WarehouseTypePO, Long> {
}