package com.smartindustry.common.mapper.im;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.im.SafeStockPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * SafeStockMapper继承基类
 */
@Mapper
public interface SafeStockMapper extends BaseMapper<SafeStockPO, Long> {
    /**
     * 批量更新
     *
     * @param updPOs
     */
    void batchUpdate(List<SafeStockPO> updPOs);
}