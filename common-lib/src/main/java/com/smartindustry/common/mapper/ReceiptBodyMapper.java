package com.smartindustry.common.mapper;

import com.smartindustry.common.bo.ReceiptBO;
import com.smartindustry.common.pojo.ReceiptBodyPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * ReceiptBodyMapper继承基类
 */
@Mapper
public interface ReceiptBodyMapper extends BaseMapper<ReceiptBodyPO, Long> {
    List<ReceiptBO> pageQuery(Map<String, Object> reqData);
}