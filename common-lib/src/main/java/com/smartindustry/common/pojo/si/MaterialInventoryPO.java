package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * si_material_inventory
 * @author 
 */
@Data
public class MaterialInventoryPO implements Serializable {
    /**
     * 物料库存信息ID
     */
    private Long materialInventoryId;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 库存状态 : 1 正常
2 缺料
3 满仓
     */
    private Byte status;

    /**
     * 库存下限
     */
    private BigDecimal lowerLimit;

    /**
     * 库存上限
     */
    private BigDecimal upperLimit;

    private static final long serialVersionUID = 1L;
}