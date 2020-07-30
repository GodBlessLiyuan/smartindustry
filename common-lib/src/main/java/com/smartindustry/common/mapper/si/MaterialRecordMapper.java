package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.MaterialRecordPO;
import com.smartindustry.common.pojo.si.SupplierRecordPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MaterialRecordMapper继承基类
 */
@Mapper
public interface MaterialRecordMapper extends BaseMapper<MaterialRecordPO, Long> {
    List<SupplierRecordPO> queryByMid(Long mid);
}