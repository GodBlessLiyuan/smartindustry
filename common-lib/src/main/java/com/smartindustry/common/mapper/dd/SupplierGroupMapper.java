package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.SupplierGroupPO;
import com.smartindustry.common.pojo.dd.WarehouseTypePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * SupplierGroupMapper继承基类
 */
@Mapper
public interface SupplierGroupMapper extends BaseMapper<SupplierGroupPO, Long> {
    List<Map<String, Object>> queryAll();

    SupplierGroupPO queryByName(String sgname);
}