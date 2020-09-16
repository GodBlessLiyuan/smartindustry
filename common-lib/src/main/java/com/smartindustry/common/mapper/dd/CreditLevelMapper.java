package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.CreditLevelPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CreditLevelMapper继承基类
 */
@Mapper
public interface CreditLevelMapper extends BaseMapper<CreditLevelPO, Long> {

    /**
     * 根据信用等级名称查询信用等级明细
     * @param name
     * @return
     */
    CreditLevelPO queryByName(String name);

    /**
     * 查询所有的信用等级明细
     * @return
     */
    List<CreditLevelPO> queryAll();
}