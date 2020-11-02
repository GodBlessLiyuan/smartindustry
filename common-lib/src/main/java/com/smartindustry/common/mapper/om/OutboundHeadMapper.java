package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * OutboundHeadMapper继承基类
 */
@Mapper
public interface OutboundHeadMapper extends BaseMapper<OutboundHeadPO, Long> {
    /**
     * 采购入库单的分页查询
     *
     * @param reqData
     * @return
     */
    List<OutboundHeadPO> pageQuery(Map<String, Object> reqData);

    /**
     * 根据出库单id查询所有得信息详情
     *
     * @param ohid
     * @return
     */
    OutboundHeadBO queryMixByOhid(Long ohid);

    /**
     * 根据 pda 类型 查询
     *
     * @param type
     * @return
     */
    List<OutboundHeadBO> queryPdaByType(Byte type);
}