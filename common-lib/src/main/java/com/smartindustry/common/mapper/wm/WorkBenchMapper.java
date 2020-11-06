package com.smartindustry.common.mapper.wm;

import com.smartindustry.common.bo.wm.WorkBenchBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.wm.WorkBenchPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mWorkBenchMapper继承基类
 */
@Mapper
public interface WorkBenchMapper extends BaseMapper<WorkBenchPO, Long> {

    /**
     * 根据工作台类型和工作台模块类型查询所有的权限列表
     *
     * @param benchModule
     * @param benchType
     * @return
     */
    List<WorkBenchBO> queryByModuleId(@Param("benchModule") Byte benchModule, @Param("benchType") Byte benchType);
}