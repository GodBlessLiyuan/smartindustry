package com.smartindustry.common.mapper.dd;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.dd.MaterialPropertyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MaterialPropertyMapper继承基类
 */
@Mapper
public interface MaterialPropertyMapper extends BaseMapper<MaterialPropertyPO, Long> {

    /**
     * 查询所有物料属性的列表
     * @return
     */
    List<MaterialPropertyPO> queryAll();

    /**
     * 判断属性名是否存在
     * @param materialPropertyName
     * @return
     */
    MaterialPropertyPO isExist(@Param("materialPropertyName") String materialPropertyName);
}