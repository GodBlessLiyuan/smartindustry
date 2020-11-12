package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.MaterialPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * MaterialMapper继承基类
 */
@Mapper
public interface MaterialMapper extends BaseMapper<MaterialPO, Long> {
    /**
     * 用于添加采购入库单表体的物料展示
     * @param reqData
     * @return
     */
    List<MaterialBO> pageQueryStorage(Map<String, Object> reqData);

    List<MaterialBO> pageQuery(Map<String, Object> reqData);
    /***
    * 根据物料编号查找物料
     * @param materialNo
     * @param materialName
     * @return
             */
    MaterialPO queryByMaterialNo(@Param("materialNo") String materialNo, @Param("materialName") String materialName);

    /**
     * 根据 物料编码 查询
     *
     * @param mno
     * @return
     */
    MaterialPO queryByMno(String mno);
    /**
     * 批量删除
     *
     * @param mids
     */
    void batchDelete(List<Long> mids);
    /**
     * 根据mid 查询
     *
     * @param mid
     * @return
     */
    MaterialBO queryByMid(Long mid);
    /**
     * 根据供应商ID 查询
     *
     * @param sids
     * @return
     */
    List<MaterialPO> queryBySids(List<Long> sids);

    List<MaterialPO> queryByMuid(Long muid);

    /***
     * 查看详情，包括单位，供应商
     * @param mid
     * @return
     */
    MaterialBO getSupperUnitByID(Long mid);

    /***
     * 查询物料类型
     * @return
     */
    List<Map<String, Object>> listAll();

    /***
     * 物料详情分页查询
     * @param reqData
     * @return
     */
    List<MaterialBO> pageList(Map<String, Object> reqData);
}