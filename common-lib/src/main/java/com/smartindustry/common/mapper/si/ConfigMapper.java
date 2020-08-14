package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.ConfigPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ConfigMapper继承基类
 */
@Mapper
public interface ConfigMapper extends BaseMapper<ConfigPO, Long> {
    /**
     * 根据 关键字 查询
     *
     * @param key
     * @return
     */
    ConfigPO queryByKey(String key);
}