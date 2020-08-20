package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.WarehouseRecordBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.WarehouseRecordPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * WarehouseRecordMapper继承基类
 */
@Mapper
public interface WarehouseRecordMapper extends BaseMapper<WarehouseRecordPO, Long> {
    /**
     * 根据wid 查询
     *
     * @param wid
     * @return
     */
    List<WarehouseRecordBO> queryByWid(Long wid);
}