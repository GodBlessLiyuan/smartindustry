package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.SupplierTypePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * SupplierTypeMapper继承基类
 */
@Mapper
public interface SupplierTypeMapper extends BaseMapper<SupplierTypePO, Long> {
    List<Map<String, Object>> queryAll();

    SupplierTypePO queryByName(String stname);
}