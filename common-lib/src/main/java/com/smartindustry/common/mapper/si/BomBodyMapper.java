package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.BomBodyBO;
import com.smartindustry.common.bo.si.BomHeadBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.BomBodyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * BomBodyMapper继承基类
 */
@Mapper
public interface BomBodyMapper extends BaseMapper<BomBodyPO, Long> {
    /**
     * 根据主BOM清单ID查询到所有的物料明细
     * @param bomHeadId
     * @return
     */
    List<BomBodyPO> queryByBhid(@Param("bomHeadId") Long bomHeadId);

    /**
     * 批量删除物料明细
     * @param pos
     * @return
     */
    Integer deleteBatch(List<BomBodyPO> pos);

    /**
     * 查询当前物料的子物料列表
     */
    List<BomHeadBO> queryChildren(@Param("parentId") Long parentId);

    /**
     *查看当前主BOM下是否已经存在当前物料的明细
     * @param materialId
     * @return
     */
    List<BomBodyPO> judgeHaveBody(@Param("materialId") Long materialId,@Param("bomHeadId") Long bomHeadId);

    /**
     * 根据主BOM的ID查询所有的物料明细
     * @param reqData
     * @return
     */
    List<BomBodyBO> pageQuery(Map<String, Object> reqData);
}