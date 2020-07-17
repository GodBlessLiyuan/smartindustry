package com.smartindustry.common.mapper.om;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.LogisticsPicturePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * LogisticsPictureMapper继承基类
 */
@Mapper
public interface LogisticsPictureMapper extends BaseMapper<LogisticsPicturePO, Long> {
    /**
     * 根据物流记录ID删除
     *
     * @param lid
     */
    void deleteBylid(Long lid);
}