package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.RecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RecordMapper继承基类
 */
@Mapper
public interface RecordMapper extends BaseMapper<RecordPO, Long> {
    /**
     * 批量插入
     *
     * @param recordPOs
     */
    void batchInsert(List<RecordPO> recordPOs);

    /**
     * 根据 receipt body id 查询
     *
     * @param receiptBodyId
     * @return
     */
    List<RecordPO> queryByReceiptBodyId(@Param("receiptBodyId") Long receiptBodyId, @Param("status") Byte status);
}