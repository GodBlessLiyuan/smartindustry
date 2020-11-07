package com.smartindustry.common.mapper.ds;

import com.smartindustry.common.bo.ds.SalesOutboundBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.ds.SalesOutboundPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * SalesOutboundMapper继承基类
 */
@Mapper
public interface SalesOutboundMapper extends BaseMapper<SalesOutboundPO, Long> {

    /**
     * 根据销售单号查找销售出库
     * @param salesNo
     * @return
     */
    SalesOutboundPO queryBySalesNo(@Param("salesNo") String salesNo);

    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<SalesOutboundBO> pageQuery(Map<String, Object> reqData);
}