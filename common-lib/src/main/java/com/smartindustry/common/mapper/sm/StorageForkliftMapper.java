package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.StorageForkliftPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * StorageForkliftMapper继承基类
 */
@Mapper
public interface StorageForkliftMapper extends BaseMapper<StorageForkliftPO, Long> {
    /**
     * 根据sids查询当前叉车数
     *
     * @param sids
     * @return
     */
    Map<Long, Integer> queryFnumBySids(List<Long> sids);
}