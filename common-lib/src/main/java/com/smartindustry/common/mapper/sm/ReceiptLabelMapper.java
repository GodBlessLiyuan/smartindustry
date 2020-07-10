package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.ReceiptLabelPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ReceiptLabelMapper继承基类
 */
@Mapper
public interface ReceiptLabelMapper extends BaseMapper<ReceiptLabelPO, Long> {
}