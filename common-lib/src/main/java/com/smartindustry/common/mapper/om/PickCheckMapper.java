package com.smartindustry.common.mapper.om;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.PickCheckPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PickCheckMapper继承基类
 */
@Mapper
public interface PickCheckMapper extends BaseMapper<PickCheckPO, Long> {
}