package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.bo.om.OutboundRecordBO;
import com.smartindustry.common.bo.sm.StorageRecordBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.StorageRecordPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * StorageRecordMapper继承基类
 */
@Mapper
public interface StorageRecordMapper extends BaseMapper<StorageRecordPO, Long> {
    /**
     * 根据入库单表头id查询操作记录
     * @param shid
     * @return
     */
    List<StorageRecordBO> queryByShid(Long shid);

    /**
     * 成品入库库查询纪录
     * @param shid
     * @return
     */
    List<StorageRecordBO> queryForkByShid(Long shid);
}