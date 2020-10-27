package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
     * 一级物料类型 : 1：原材料
2：半成品
3：成品
     */
    private Byte materialType;

    /**
     * 二级物料类型ID
     */
    private Long materialTypeId;

    /**
     * 湿度等级ID
     */
    private Long humidityLevelId;

    /**
     * 物料层级ID
     */
    private Long materialLevelId;

    /**
     * 计量单位ID
     */
    private Long measureUnitId;

    /**
     * 物料版本ID
     */
    private Long materialVersionId;

    /**
     * 生产损耗等级ID
     */
    private Long produceLossLevelId;

    /**
     * 生命周期状态ID
     */
    private Long lifeCycleStateId;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 交期天数
     */
    private Integer deliveryDays;

    /**
     * MOQ
     */
    private String moq;

    /**
     * 规格型号
     */
    private String materialModel;

    /**
     * 物料图号
     */
    private String materialDraw;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 检验类型
     */
    private Byte testType;

    /**
     * 创建人
     */
    private Long userId;

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

    private static final long serialVersionUID = 1L;
}