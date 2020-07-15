package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.StorageLabelPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * StorageLabelMapper继承基类
 */
@Mapper
public interface StorageLabelMapper extends BaseMapper<StorageLabelPO, Long> {
    /**
     * 批量插入
     *
     * @param storageLabelPOs
     */
    void batchInsert(List<StorageLabelPO> storageLabelPOs);
}