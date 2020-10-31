package com.smartindustry.common.mapper.pda;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.pda.OutboundForkliftPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * OutboundForkliftMapper继承基类
 */
@Mapper
public interface OutboundForkliftMapper extends BaseMapper<OutboundForkliftPO, Long> {
    /**
     * 根据 叉车ID 删除
     *
     * @param forkliftId
     */
    void deleteByFid(Long forkliftId);

    /**
     * 根据 叉车ID 查询
     *
     * @param forkliftId
     * @return
     */
    OutboundForkliftPO queryByFid(Long forkliftId);
}