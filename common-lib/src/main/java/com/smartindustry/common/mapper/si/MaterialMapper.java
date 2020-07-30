package com.smartindustry.common.mapper.si;

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
    List<MaterialPO> pageQuery(Map<String, Object> reqData);

    /**
     * 根据供应商ID 查询
     *
     * @param sids
     * @return
     */
    List<MaterialPO> queryBySids(List<Long> sids);
}