package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.ClientRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClientRecordMapper继承基类
 */
@Mapper
public interface ClientRecordMapper extends BaseMapper<ClientRecordPO, Long> {
}