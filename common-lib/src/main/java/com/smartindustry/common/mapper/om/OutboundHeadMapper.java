package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    List<OutboundHeadBO> queryMixByOhid(Long ohid);

    /**
     * 根据 pda类型 查询出库列表数据
     *
     * @param type
     * @return
     */
    List<OutboundHeadBO> queryOlistByPdaType(Byte type);

    /**
     * @param ohid
     * @return
     */
    OutboundHeadBO queryByOhid(Long ohid);

    /**
     * PC端成品入库单的分页查询
     * @param reqData
     * @return
     */
    List<OutboundHeadBO> pageQueryPro(Map<String, Object> reqData);

    /**
     * 出库单查看详情
     * @param ohid
     * @return
     */
    OutboundHeadBO queryDetail(Long ohid);

    /**
     * 根据入库单号查询入库单表头
     * @param outboundNo
     * @return
     */
    OutboundHeadPO queryByOutboundNo(@Param("outboundNo") String outboundNo);
}