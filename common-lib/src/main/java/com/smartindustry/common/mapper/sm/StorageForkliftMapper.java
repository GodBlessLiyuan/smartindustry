package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.bo.sm.StorageForkliftBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.StorageForkliftPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 根据叉车id查询当前正在运行的叉车数记录
     *
     * @param fid
     * @return
     */
    StorageForkliftPO queryByFid(Long fid);

    /**
     * 根据 入库表头ID 查询
     *
     * @param shid
     * @return
     */
    List<StorageForkliftBO> queryByShid(@Param("shid") Long shid);
}