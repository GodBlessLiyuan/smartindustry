package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.PrintLabelPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PrintLabelMapper继承基类
 */
@Mapper
public interface PrintLabelMapper extends BaseMapper<PrintLabelPO, Long> {
}