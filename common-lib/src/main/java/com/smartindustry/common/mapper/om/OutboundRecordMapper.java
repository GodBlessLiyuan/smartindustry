package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.OutboundRecordBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.OutboundRecordPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * OutboundRecordMapper继承基类
 */
@Mapper
public interface OutboundRecordMapper extends BaseMapper<OutboundRecordPO, Long> {
    /**
     * 根据出库单id查询操作记录
     * @param ohid
     * @return
     */
    List<OutboundRecordBO> queryByOhid(Long ohid);

    /**
     * 成品出库查询纪录
     * @param ohid
     * @return
     */
    List<OutboundRecordBO> queryForkByOhid(Long ohid);
}