package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.LocationPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * LocationMapper继承基类
 */
@Mapper
public interface LocationMapper extends BaseMapper<LocationPO, Long> {
    /**
     * 根据库位查询
     *
     * @param locationNo
     * @return
     */
    LocationPO queryByLno(String locationNo);
}