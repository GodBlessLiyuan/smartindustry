package com.smartindustry.common.mapper.am;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.DeptPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * DeptMapper继承基类
 */

@Mapper
public interface DeptMapper extends BaseMapper<DeptPO, Long> {

    List<DeptPO> deptPageQuery(Map<String, Object> reqData);
}