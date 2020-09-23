package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.PickLabelPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PickLabelMapper继承基类
 */
@Mapper
public interface PickLabelMapper extends BaseMapper<PickLabelPO, Long> {
    /**
     * 根据phid查询
     *
     * @param pickHeadId
     * @return
     */
    List<PrintLabelBO> queryByPhid(Long pickHeadId);

    /**
     * 查询已被锁定的数据
     *
     * @param pickHeadId
     * @return
     */
    List<PrintLabelBO> queryLockByPhid(Long pickHeadId);

    /**
     * 查看pid是否已经在当前的工单拣货单下
     * @param printLabelId
     * @return
     */
    PickLabelPO queryPickLabel(@Param("printLabelId") Long printLabelId);
}