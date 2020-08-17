package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.MaterialLockPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * MaterialLockMapper继承基类
 */
@Mapper
public interface MaterialLockMapper extends BaseMapper<MaterialLockPO, Long> {
    List<Map<String, Object>> queryAll();

    MaterialLockPO queryByName(String mlkname);
}