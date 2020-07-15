package com.smartindustry.common.mapper.om;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.PickLabelPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PickLabelMapper继承基类
 */
@Mapper
public interface PickLabelMapper extends BaseMapper<PickLabelPO, Long> {
}