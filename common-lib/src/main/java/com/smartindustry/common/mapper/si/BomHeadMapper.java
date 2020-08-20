package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.BomHeadBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.BomHeadPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * BomHeadMapper继承基类
 */
@Mapper
public interface BomHeadMapper extends BaseMapper<BomHeadPO, Long> {

    /**
     * 物料清单管理的分页查询
     * @param reqData
     * @return
     */
    List<BomHeadBO> pageQuery(Map<String, Object> reqData);

    /**
     * 判断当前物料已经形成主BOM清单
     * @param materialId
     * @return
     */
    List<BomHeadPO> judgeHaveBom(@Param("materialId") Long materialId);

    /**
     * 根据主BOM的ID查询主物料
     * @param bomHeadId
     * @return
     */
    BomHeadBO queryMainMaterial(@Param("bomHeadId") Long bomHeadId);

    /**
     * 删除主物料，假删
     * @param bhids
     * @return
     */
    Integer deleteBatch(List<Long> bhids);
}