package com.smartindustry.common.mapper;

import com.smartindustry.common.bo.StorageDetailBO;
import com.smartindustry.common.pojo.StorageDetailPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * StorageDetailMapper继承基类
 */
@Mapper
public interface StorageDetailMapper extends BaseMapper<StorageDetailPO, Long> {
    /**
     * 根据 groupId 查询
     *
     * @param storageGroupId
     * @return
     */
    List<StorageDetailBO> queryByGroupId(Long storageGroupId);
}