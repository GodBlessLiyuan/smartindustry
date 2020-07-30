package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.SupplierBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.SupplierPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * SupplierMapper继承基类
 */
@Mapper
public interface SupplierMapper extends BaseMapper<SupplierPO, Long> {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<SupplierBO> pageQuery(Map<String, Object> reqData);

    /**
     * 根据 编号 查询
     *
     * @param sno
     * @return
     */
    SupplierPO queryBySno(String sno);
}