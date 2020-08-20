package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.LocationRecordBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.LocationRecordPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * LocationRecordMapper继承基类
 */
@Mapper
public interface LocationRecordMapper extends BaseMapper<LocationRecordPO, Long> {
    /**
     * 根据库位ID查询
     *
     * @param lid
     * @return
     */
    List<LocationRecordBO> queryByLid(Long lid);
}