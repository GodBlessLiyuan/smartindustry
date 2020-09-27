package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.MaterialAttributeBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.MaterialAttributePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MaterialAttributeMapper继承基类
 */
@Mapper
public interface MaterialAttributeMapper extends BaseMapper<MaterialAttributePO, Long> {
    MaterialAttributeBO detail(Long materialId);

    /**
     * 根据 物料Id 查询
     *
     * @param materialId
     * @return
     */
    MaterialAttributePO queryByMid(Long materialId);
}