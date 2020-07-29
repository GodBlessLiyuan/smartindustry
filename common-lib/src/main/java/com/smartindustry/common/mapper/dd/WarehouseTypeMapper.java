package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.WarehouseTypePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * WarehouseTypeMapper继承基类
 */
@Mapper
public interface WarehouseTypeMapper extends BaseMapper<WarehouseTypePO, Long> {
    /**
     * 查询所有数据
     *
     * @return
     */
    List<Map<String, Object>> queryAll();

    /**
     * 根据名称查询
     *
     * @param wtname
     * @return
     */
    WarehouseTypePO queryByName(String wtname);
}