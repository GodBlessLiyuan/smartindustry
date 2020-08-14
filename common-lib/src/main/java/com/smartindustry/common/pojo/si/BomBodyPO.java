package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_bom_body
 * @author 
 */
@Data
public class BomBodyPO implements Serializable {
    private Long bomBodyId;

    private Long bomHeadId;

    private Long materialId;

    private Long materialPropertyId;

    private Float materialDemand;

    /**
     * 1：配比/比例
2：计数
     */
    private Byte demandType;

    private Float materialLoss;

    /**
     * 1：配比/比例
2：计数
     */
    private Byte lossType;

    private Long processId;

    private Long parentId;

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