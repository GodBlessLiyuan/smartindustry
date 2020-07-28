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
    private Long materialId;

    private String materialNo;

    private Long materialTypeId;

    private Long humidityLevelId;

    private Long materialLevelId;

    private Long measureUnitId;

    private Long materialVersionId;

    private Long produceLossLevelId;

    private Long liftCycleStateId;

    private String materialName;

    private Integer deliveryDays;

    private String moq;

    private String materialModel;

    private String materialDraw;

    private Long supplierId;

    private String materialDesc;

    private Byte testType;

    private Long userId;

    private Date createTime;

    private Date updateTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}