package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.MaterialAttributeBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.MaterialAttributePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 根据 物料Ids 查询
     *
     * @param mids
     * @return
     */
    List<MaterialAttributePO> queryByMids(List<Long> mids);

    /**
     * 批量更新
     *
     * @param pos
     */
    void batchUpdate(ArrayList<MaterialAttributePO> pos);
}