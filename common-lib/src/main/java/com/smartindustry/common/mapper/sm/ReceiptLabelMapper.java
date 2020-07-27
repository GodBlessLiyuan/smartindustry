package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.ReceiptLabelPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ReceiptLabelMapper继承基类
 */
@Mapper
public interface ReceiptLabelMapper extends BaseMapper<ReceiptLabelPO, Long> {
    /**
     * 根据 printLabelId 查询
     *
     * @param printLabelId
     * @return
     */
    ReceiptLabelPO queryByPrintLabelId(Long printLabelId);

    /**
     * 根据 printLabelId 更新 sid
     *
     * @param sid
     * @param plIds
     */
    void updateSidByPlids(@Param("sid") Long sid, @Param("plIds") List<Long> plIds);

    /**
     * 根据 rbids 删除数据
     *
     * @param rbIds
     */
    void batchDeleteByRbid(List<Long> rbIds);
}