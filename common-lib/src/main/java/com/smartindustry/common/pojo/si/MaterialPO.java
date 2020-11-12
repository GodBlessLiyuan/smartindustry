package com.smartindustry.common.pojo.si;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * si_material
 * @author 
 */
@Data
public class MaterialPO implements Serializable {
    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 物料编号
     */
    private String materialNo;

    /**
     * 物料类型 : 1：原材料
2：成品
     */
    private Byte materialType;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 物料等级
     */
    private String materialLevel;

    /**
     * 规格型号
     */
    private String materialModel;

    /**
     * 计量单位ID
     */
    private Long measureUnitId;

    /**
     * 包装体积
     */
    private BigDecimal packageVolume;

    /**
     * 物料批次
     */
    private String materialBatch;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 : 1：未删除
2：已删除
     */
    private Byte dr;

    private BigDecimal price;

    private static final long serialVersionUID = 1L;
}