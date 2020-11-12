package com.smartindustry.common.mapper.om;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OutboundBodyMapper继承基类
 */
@Mapper
public interface OutboundBodyMapper extends BaseMapper<OutboundBodyPO, Long> {
    /**
     * 批量更新
     *
     * @param pos
     */
    void batchUpdate(List<OutboundBodyPO> pos);

    /**
     * 根据表头ID进行查询
     *
     * @param ohid
     * @param mid
     * @return
     */
    OutboundBodyPO queryByOhidAndMid(@Param("ohid") Long ohid,@Param("mid") Long mid);
}