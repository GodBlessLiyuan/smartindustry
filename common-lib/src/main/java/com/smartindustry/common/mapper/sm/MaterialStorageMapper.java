package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.bo.sm.MaterialStorageBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.MaterialStoragePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * MaterialStorageMapper继承基类
 */
@Mapper
public interface MaterialStorageMapper extends BaseMapper<MaterialStoragePO, Long> {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<MaterialStorageBO> pageQuery(Map<String, Object> reqData);
}