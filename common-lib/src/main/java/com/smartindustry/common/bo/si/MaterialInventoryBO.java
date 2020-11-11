package com.smartindustry.common.bo.si;

import com.smartindustry.common.pojo.si.MaterialInventoryPO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:52 2020/11/11
 * @version: 1.0.0
 * @description:
 */
public class MaterialInventoryBO extends MaterialInventoryPO {
    /**
     * 物料编号
     */
    private String materialNo;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 物料类型
     */
    private Byte materialType;
    /**
     * 物料型号
     */
    private String materialModel;

    private String materialDesc;
    /**
     * 包装体积
     */
    private BigDecimal packageVolume;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 仓库id
     */
    private Long warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 库存状态
     */
    private Byte status;
    /**
     * 库存数量
     */
    private BigDecimal inventoryQuantity;
    /**
     * 计量单位
     */
    private String measureUnitName;

}
