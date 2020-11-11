package com.smartindustry.common.sqlserver;

import com.smartindustry.common.pojo.ds.sqlserver.MaterialErpPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MaterialErpMapper {
    List<MaterialErpPO> queryAll();

    MaterialErpPO queryByNo(@Param("materialNo") String materialNo, @Param("materialName") String materialName);
}