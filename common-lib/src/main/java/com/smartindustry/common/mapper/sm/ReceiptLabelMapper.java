package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.ReceiptLabelPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ReceiptLabelMapper继承基类
 */
@Mapper
public interface ReceiptLabelMapper extends BaseMapper<ReceiptLabelPO, Long> {
    /**
     * 批量插入
     *
     * @param rlPOs
     */
    void batchInsert(List<ReceiptLabelPO> rlPOs);

    /**
     * 根据 printLabelId 查询
     *
     * @param printLabelId
     * @return
     */
    ReceiptLabelPO queryByPrintLabelId(Long printLabelId);
}