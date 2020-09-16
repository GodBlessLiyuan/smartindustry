package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.ClientTypePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClientTypeMapper继承基类
 */
@Mapper
public interface ClientTypeMapper extends BaseMapper<ClientTypePO, Long> {
    /**
     * 根据信用等级名称查询信用等级明细
     * @param name
     * @return
     */
    ClientTypePO queryByName(String name);

    /**
     * 查询所有的信用等级明细
     * @return
     */
    List<ClientTypePO> queryAll();
}