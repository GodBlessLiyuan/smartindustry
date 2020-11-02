package com.smartindustry.common.mapper.wo;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.wo.ProduceOrderPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ProduceOrderMapper继承基类
 */
@Mapper
public interface ProduceOrderMapper extends BaseMapper<ProduceOrderPO, Long> {
}