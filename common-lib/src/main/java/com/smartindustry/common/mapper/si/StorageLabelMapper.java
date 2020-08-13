package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.StorageLabelBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.StorageLabelPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * StorageLabelMapper继承基类
 */
@Mapper
public interface StorageLabelMapper extends BaseMapper<StorageLabelPO, Long> {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<StorageLabelBO> pageQuery(Map<String, Object> reqData);

    /**
     * 查询尚未推荐的入库标签
     *
     * @param orderNo
     * @param materialId
     * @return
     */
    List<StorageLabelBO> queryNotRecommend(@Param("orderNo") String orderNo, @Param("materialId") Long materialId);

    /**
     * 根据 货位id 查询
     *
     * @param lids
     * @return
     */
    List<StorageLabelPO> queryByLids(List<Long> lids);

    /**
     * 根据标签ID删除
     *
     * @param plIds
     */
    void deleteByPlids(List<Long> plIds);

    /**
     * 批量更新状态
     *
     * @param slids
     * @param status
     */
    void updateStatus(@Param("slids") ArrayList<Long> slids, @Param("status") Byte status);
}