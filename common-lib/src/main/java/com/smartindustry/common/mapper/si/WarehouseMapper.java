package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.WarehouseBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.WarehousePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * WarehouseMapper继承基类
 */
@Mapper
public interface WarehouseMapper extends BaseMapper<WarehousePO, Long> {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<WarehouseBO> pageQuery(Map<String, Object> reqData);

    /**
     * 根据ID查询
     *
     * @param wid
     * @return
     */
    WarehouseBO queryById(Long wid);
}