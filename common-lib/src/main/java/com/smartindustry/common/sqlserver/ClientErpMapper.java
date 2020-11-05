package com.smartindustry.common.sqlserver;

import com.smartindustry.common.pojo.ds.sqlserver.ClientErpPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClientErpMapper {

    List<ClientErpPO> queryAll();
}