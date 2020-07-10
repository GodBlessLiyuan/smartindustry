package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.bo.sm.StorageBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.StoragePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * StorageMapper继承基类
 */
@Mapper
public interface StorageMapper extends BaseMapper<StoragePO, Long> {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<StorageBO> pageQuery(Map<String, Object> reqData);
}