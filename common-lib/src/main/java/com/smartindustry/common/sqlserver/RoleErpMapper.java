package com.smartindustry.common.sqlserver;

import com.smartindustry.common.pojo.ds.sqlserver.RoleErpPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleErpMapper {
    List<RoleErpPO> queryAll();
}