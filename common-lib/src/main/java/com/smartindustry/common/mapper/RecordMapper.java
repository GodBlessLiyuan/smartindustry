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
     * 根据 receipt body id 查询， 根据 order 进行排序
     *
     * @param receiptBodyId
     * @param order
     * @return
     */
    List<RecordPO> queryByReceiptBodyId(@Param("receiptBodyId") Long receiptBodyId, @Param("order") byte order);
}