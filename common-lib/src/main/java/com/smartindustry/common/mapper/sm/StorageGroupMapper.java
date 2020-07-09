package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.bo.sm.StorageGroupBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.StorageGroupPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * StorageGroupMapper继承基类
 */
@Mapper
public interface StorageGroupMapper extends BaseMapper<StorageGroupPO, Long> {
    /**
     * 物料入库详情
     *
     * @param storageId
     * @return
     */
    List<StorageGroupBO> queryBySid(Long storageId);
}