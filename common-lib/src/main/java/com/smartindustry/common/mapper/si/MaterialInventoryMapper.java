package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.MaterialInventoryBO;
import com.smartindustry.common.bo.si.ProductDetailBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.MaterialInventoryPO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * 查询所有物料的当前库存数量
     * @return
     */
    @MapKey("mid")
    Map<BigInteger, Map<Long, BigDecimal>> queryMaterialMap();

    /**
     * 安全库存表的批量删除
     * @param mids
     */
    void deleteBatch(@Param("mids") Set<Long> mids);

}