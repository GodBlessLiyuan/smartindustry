package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.ReceiptHeadPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ReceiptHeadMapper继承基类
 */
@Mapper
public interface ReceiptHeadMapper extends BaseMapper<ReceiptHeadPO, Long> {
}