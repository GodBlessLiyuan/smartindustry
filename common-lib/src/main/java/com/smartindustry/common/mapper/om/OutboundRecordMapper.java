package com.smartindustry.common.mapper.om;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.OutboundRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OutboundRecordMapper继承基类
 */
@Mapper
public interface OutboundRecordMapper extends BaseMapper<OutboundRecordPO, Long> {
    /**
     * 根据出库单id查询
     *
     * @param oId
     * @return
     */
    List<OutboundRecordPO> queryByOid(Long oId);

    /**
     * 根据工单号id查找
     * @param pickHeadId
     * @param status
     * @return
     */
    List<OutboundRecordPO> queryByPhid(@Param("pickHeadId") Long pickHeadId, @Param("status") Byte status);
}