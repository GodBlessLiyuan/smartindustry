package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.MaterialPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * MaterialMapper继承基类
 */
@Mapper
public interface MaterialMapper extends BaseMapper<MaterialPO, Long> {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<MaterialBO> pageQuery(Map<String, Object> reqData);

    /**
     * 根据供应商ID 查询
     *
     * @param sids
     * @return
     */
    List<MaterialPO> queryBySids(List<Long> sids);

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
     * @param sids
     */
    void batchDelete(List<Long> sids);

    /**
     * 根据mid 查询
     *
     * @param mid
     * @return
     */
    MaterialBO queryByMid(Long mid);

    List<MaterialPO> queryByMtid(Long mtid);

    List<MaterialPO> queryByHlid(Long hlid);

    List<MaterialPO> queryByMlid(Long mlid);

    List<MaterialPO> queryByMuid(Long muid);

    List<MaterialPO> queryByMvid(Long mvid);

    List<MaterialPO> queryByPllid(Long pllid);

    List<MaterialPO> queryByLcsid(Long lcsid);


    /**
     * 用于添加bom清单时的物料展示
     * @param reqData
     * @return
     */
    List<MaterialBO> pageQueryBom(Map<String, Object> reqData);
}