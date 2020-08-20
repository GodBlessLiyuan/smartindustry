package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.BomRecordBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.BomRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BomRecordMapper继承基类
 */
@Mapper
public interface BomRecordMapper extends BaseMapper<BomRecordPO, Long> {
    /**
     * 根据bhid查询主物料的操作记录
     * @param bomHeadId
     * @return
     */
    List<BomRecordBO> queryByBhid(@Param("bomHeadId") Long bomHeadId);
}