package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.ProcessPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ProcessMapper继承基类
 */
@Mapper
public interface ProcessMapper extends BaseMapper<ProcessPO, Long> {

    /**
     * 查询所有物料工序的列表
     * @return
     */
    List<ProcessPO> queryAll();

    /**
     * 判断工序名是否存在
     * @param processName
     * @return
     */
    ProcessPO isExist(@Param("processName") String processName);
}