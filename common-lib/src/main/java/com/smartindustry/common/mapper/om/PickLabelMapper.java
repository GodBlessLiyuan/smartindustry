package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.PickLabelPO;
import org.apache.ibatis.annotations.Mapper;

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
}