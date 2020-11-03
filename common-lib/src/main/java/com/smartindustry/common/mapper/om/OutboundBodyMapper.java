package com.smartindustry.common.mapper.om;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * OutboundBodyMapper继承基类
 */
@Mapper
public interface OutboundBodyMapper extends BaseMapper<OutboundBodyPO, Long> {
    /**
     * 批量更新
     * @param pos
     */
    void batchUpdate(List<OutboundBodyPO> pos);
}