package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.MixHeadBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.MixHeadPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MixHeadMapper继承基类
 */
@Mapper
public interface MixHeadMapper extends BaseMapper<MixHeadPO, Long> {
    /**
     * 查询当天的混料工单
     * @return
     */
    List<MixHeadBO> queryMix();
}