package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.LocationPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
}