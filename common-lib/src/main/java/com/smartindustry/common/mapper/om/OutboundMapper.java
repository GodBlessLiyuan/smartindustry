package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.OutboundBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.OutboundPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

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

    /**
     * 根据oid查询
     *
     * @param oId
     * @return
     */
    OutboundBO queryByOid(@Param("oId") Long oId);


    /**
     * 根据出库单查询出库单列表
     *
     * @param status
     * @return
     */
    Integer countHandleNum(@Param("status") Byte status);
}