package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.MaterialPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * MaterialMapper继承基类
 */
@Mapper
public interface MaterialMapper extends BaseMapper<MaterialPO, Long> {

    List<MaterialBO> pageQueryStorage(Map<String, Object> reqData);

    /**
     * 根据物料编号查找物料
     * @param materialNo
     * @param materialName
     * @return
     */
    MaterialPO queryByMaterialNo(@Param("materialNo") String materialNo, @Param("materialName") String materialName);

}