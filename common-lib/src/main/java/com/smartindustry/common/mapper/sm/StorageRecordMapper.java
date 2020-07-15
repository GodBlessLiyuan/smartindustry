package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.StorageRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * StorageRecordMapper继承基类
 */
@Mapper
public interface StorageRecordMapper extends BaseMapper<StorageRecordPO, Long> {
    /**
     * 批量插入
     *
     * @param recordPOs
     */
    void batchInsert(List<StorageRecordPO> recordPOs);

    /**
     * 根据 receipt body id 查询
     *
     * @param receiptBodyId
     * @return
     */
    List<StorageRecordPO> queryByReceiptBodyId(@Param("receiptBodyId") Long receiptBodyId, @Param("status") Byte status);

    /**
     * 根据 storageId 查询
     *
     * @param sid
     * @return
     */
    List<StorageRecordPO> queryBySid(Byte sid);
}