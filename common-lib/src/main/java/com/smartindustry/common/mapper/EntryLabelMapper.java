package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.EntryLabelPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * EntryLabelMapper继承基类
 */
@Mapper
public interface EntryLabelMapper extends BaseMapper<EntryLabelPO, Long> {
    /**
     * 批量插入
     *
     * @param labelPOs
     */
    void batchInsert(List<EntryLabelPO> labelPOs);
}