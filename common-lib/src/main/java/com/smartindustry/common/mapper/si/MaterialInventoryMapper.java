package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.MaterialInventoryBO;
import com.smartindustry.common.bo.si.ProductDetailBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.MaterialInventoryPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * MaterialInventoryMapper继承基类
 */
@Mapper
public interface MaterialInventoryMapper extends BaseMapper<MaterialInventoryPO, Long> {
    /**
     * 物料库存管理的分页查询
     * @param reqData
     * @return
     */
    List<MaterialInventoryBO> pageQuery(Map<String, Object> reqData);

    /**
     * 安全库存上下限的批量更新
     * @param pos
     */
    void updateBatch(List<MaterialInventoryPO> pos);

    /**
     * 成品库存明细查询分页查询
     * @param reqData
     * @return
     */
    List<ProductDetailBO> pageQueryPro(Map<String, Object> reqData);
}