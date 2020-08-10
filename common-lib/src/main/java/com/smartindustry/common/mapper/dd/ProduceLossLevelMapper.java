package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.ProduceLossLevelPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * ProduceLossLevelMapper继承基类
 */
@Mapper
public interface ProduceLossLevelMapper extends BaseMapper<ProduceLossLevelPO, Long> {
    List<Map<String, Object>> queryAll();

    ProduceLossLevelPO queryByName(String pllname);
}