package com.smartindustry.common.sqlserver;

import com.smartindustry.common.pojo.ds.sqlserver.OperatorPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OperatorMapper {
    List<OperatorPO> queryAll();
}