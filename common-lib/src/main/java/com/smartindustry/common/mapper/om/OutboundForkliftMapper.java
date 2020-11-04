package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.OutboundForkliftBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.OutboundForkliftPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 根据 出库表头ID 查询
     *
     * @param outboundHeadId
     * @return
     */
    List<OutboundForkliftBO> queryByOhid(Long outboundHeadId);
}