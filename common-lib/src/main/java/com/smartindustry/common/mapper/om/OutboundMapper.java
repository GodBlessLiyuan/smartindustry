package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.OutboundBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.OutboundPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * OutboundMapper继承基类
 */
@Mapper
public interface OutboundMapper extends BaseMapper<OutboundPO, Long> {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<OutboundBO> pageQuery(Map<String, Object> reqData);
}