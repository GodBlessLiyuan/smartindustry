package com.smartindustry.common.sqlserver;

import com.smartindustry.common.pojo.ds.sqlserver.DeptErpPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptErMapper {
    List<DeptErpPO> queryAll();
}