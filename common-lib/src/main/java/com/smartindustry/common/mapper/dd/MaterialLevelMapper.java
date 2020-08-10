package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.MaterialLevelPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * MaterialLevelMapper继承基类
 */
@Mapper
public interface MaterialLevelMapper extends BaseMapper<MaterialLevelPO, Long> {
    List<Map<String, Object>> queryAll();

    MaterialLevelPO queryByName(String mlname);
}