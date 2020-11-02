package com.smartindustry.common.mapper.wo;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.wo.PackagePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PackageMapper继承基类
 */
@Mapper
public interface PackageMapper extends BaseMapper<PackagePO, Long> {
}