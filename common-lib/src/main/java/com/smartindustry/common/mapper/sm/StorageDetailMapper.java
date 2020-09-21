package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.bo.sm.StorageDetailBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.StorageDetailPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 根据 plid 查询
     *
     * @param printLabelId
     * @return
     */
    StorageDetailPO queryByPlId(Long printLabelId);

    /**
     * 根据入库单删除入库详情
     * @param storageId
     */
    void deleteBySid(@Param("storageId") Long storageId);

    /**
     * 根据入库详情组ID 列表查询入库详情
     * @param sgIds
     * @return
     */
    List<StorageDetailBO> queryByGroupIds(List<Long> sgIds);
}