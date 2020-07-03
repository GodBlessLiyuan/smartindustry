package com.smartindustry.common.mapper;

import com.smartindustry.common.bo.MaterialStorageBO;
import com.smartindustry.common.pojo.MaterialStoragePO;
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