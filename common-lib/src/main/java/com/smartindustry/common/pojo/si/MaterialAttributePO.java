package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * si_material_attribute
 * @author 
 */
@Data
public class MaterialAttributePO implements Serializable {
    /**
     * 物料属性ID
     */
    private Long materialAttributeId;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 库存下限
     */
    private BigDecimal lowerLimit;

    /**
     * 库存上限
     */
    private BigDecimal upperLimit;

    /**
     * 默认采购
     */
    private BigDecimal defaultPurchase;

    /**
     * 是否在途 : 1：是
2：否
     */
    private Byte way;

    /**
     * 默认仓库
     */
    private Long warehouseId;

    /**
     * 默认库位
     */
    private Long locationId;

    /**
     * 入库质量检验 : 1：IQC检验
2：QE检验
     */
    private Byte storageInspect;

    /**
     * 入库质量检验类型
     */
    private Byte storageInspectType;

    /**
     * 入库抽样计划
     */
    private Byte storageSamplingPlan;

    /**
     * 出库质量检验 : 1：工单审核
2：OQC检验
     */
    private Byte outboundInspect;

    /**
     * 拣货时不支持分料 : 1：是
2：否
     */
    private Byte pickSplit;

    private static final long serialVersionUID = 1L;
}