package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.WarehouseBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.WarehousePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * WarehouseMapper继承基类
 */
@Mapper
public interface WarehouseMapper extends BaseMapper<WarehousePO, Long> {

    /**
     * 查询所有的仓库和储位，必须要是有储位的仓库
     * @return
     */
    List<WarehouseBO> queryAll();
}