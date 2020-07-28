package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.WarehousePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * WarehouseMapper继承基类
 */
@Mapper
public interface WarehouseMapper extends BaseMapper<WarehousePO, Long> {
}