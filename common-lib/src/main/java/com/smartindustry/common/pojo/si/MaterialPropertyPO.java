package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * si_material_property
 *
 * @author
 */
@Data
public class MaterialPropertyPO implements Serializable {
    private Long materialPropertyId;

    private BigDecimal lowerLimit;

    private BigDecimal upperLimit;

    private BigDecimal defaultPurchase;

    /**
     * 1：是
     * 2：否
     */
    private Byte way;

    private Long warehouseId;

    private Long locationId;

    /**
     * 1：IQC检验
     * 2：QE检验
     */
    private Byte storageInspect;

    private Byte storageInspectType;

    private Byte storageSamplingPlan;

    /**
     * 1：工单审核
     * 2：OQC检验
     */
    private Byte outboundInspect;

    /**
     * 1：是
     * 2：否
     */
    private Byte pickSplit;

    private static final long serialVersionUID = 1L;
}