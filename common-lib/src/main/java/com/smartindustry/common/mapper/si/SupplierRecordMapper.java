package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.SupplierRecordBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.SupplierRecordPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * SupplierRecordMapper继承基类
 */
@Mapper
public interface SupplierRecordMapper extends BaseMapper<SupplierRecordPO, Long> {
    /**
     * 根据SId 查询
     *
     * @param sid
     * @return
     */
    List<SupplierRecordBO> queryBySid(Long sid);
}