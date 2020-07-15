package com.smartindustry.common.mapper.om;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.PickBodyPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * PickBodyMapper继承基类
 */
@Mapper
public interface PickBodyMapper extends BaseMapper<PickBodyPO, Long> {
}