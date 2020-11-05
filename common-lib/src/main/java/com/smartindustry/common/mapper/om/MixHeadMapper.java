package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.MixHeadBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.MixHeadPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * MixHeadMapper继承基类
 */
@Mapper
public interface MixHeadMapper extends BaseMapper<MixHeadPO, Long> {

}