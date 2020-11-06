package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.SupplierPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * SupplierMapper继承基类
 */
@Mapper
public interface SupplierMapper extends BaseMapper<SupplierPO, Long> {
    /**
     * 根据供应商编码 查询供应商
     *
     * @param supplierNo
     * @return
     */
    SupplierPO queryBySno(@Param("supplierNo") String supplierNo);
}