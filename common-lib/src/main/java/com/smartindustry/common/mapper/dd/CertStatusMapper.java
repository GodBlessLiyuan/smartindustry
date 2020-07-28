package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.CertStatusPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * CertStatusMapper继承基类
 */
@Mapper
public interface CertStatusMapper extends BaseMapper<CertStatusPO, Long> {
}