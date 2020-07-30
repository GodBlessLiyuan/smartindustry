package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.LocationPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * LocationMapper继承基类
 */
@Mapper
public interface LocationMapper extends BaseMapper<LocationPO, Long> {
    /**
     * 根据库位编号查询
     *
     * @param locationNo
     * @return
     */
    LocationPO queryByLno(String locationNo);

    /**
     * 根据仓库ID查询
     *
     * @param wids
     * @return
     */
    List<LocationBO> queryByWids(List<Long> wids);

    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<LocationBO> pageQuery(Map<String, Object> reqData);

    /**
     * 批量删除
     *
     * @param lids
     */
    void batchDelete(List<Long> lids);

    /**
     * 根据Id查询
     *
     * @param lid
     * @return
     */
    LocationBO queryById(Long lid);
}