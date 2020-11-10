package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.ForkliftRecordBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.ForkliftRecordPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ForkliftRecordMapper继承基类
 */
@Mapper
public interface ForkliftRecordMapper extends BaseMapper<ForkliftRecordPO, Long> {
    /***
     * 查询叉车操作记录
     * @param fid
     * @return
     */
    List<ForkliftRecordBO> listRecord(Long fid);
}