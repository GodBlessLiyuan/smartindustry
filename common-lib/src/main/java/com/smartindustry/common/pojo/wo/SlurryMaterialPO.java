package com.smartindustry.common.pojo.wo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * wo_slurry_material
 * @author 
 */
@Data
public class SlurryMaterialPO implements Serializable {
    /**
     * 料浆原材料ID
     */
    private Long slurryMaterialId;

    /**
     * 料浆工单ID
     */
    private Long slurryId;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 需求量
     */
    private BigDecimal needNum;

    /**
     * 计划出库数量
     */
    private BigDecimal planNum;

    private static final long serialVersionUID = 1L;
}