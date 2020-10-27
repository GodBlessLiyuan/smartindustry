package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.ClientPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClientMapper继承基类
 */
@Mapper
public interface ClientMapper extends BaseMapper<ClientPO, Long> {
}