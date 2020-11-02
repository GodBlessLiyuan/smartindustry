package com.smartindustry.common.pojo.wo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * wo_produce_order
 * @author 
 */
@Data
public class ProduceOrderPO implements Serializable {
    /**
     * 成品工单ID
     */
    private Long produceOrderId;

    /**
     * 工单编号
     */
    private String produceNo;

    /**
     * 计划生产日期
     */
    private Date planDate;

    /**
     * 状态 : 1. 待下发
2. 下发中
3. 待生产
4. 生产备料中
5. 生产中
6. 已完成
     */
    private Byte status;

    /**
     * 工单完成时间
     */
    private Date finishTime;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 工单开始时间
     */
    private Date beginTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 产品1
     */
    private Long materialId1;

    /**
     * 产品2
     */
    private Long materialId2;

    /**
     * 生产数量（釜）
     */
    private Integer produceNum;

    /**
     * 料浆（kg)
     */
    private BigDecimal slurry;

    /**
     * 水泥(kg)
     */
    private BigDecimal concrete;

    /**
     * 石灰（kg）
     */
    private BigDecimal maintainFlour;

    /**
     * 外加剂(ml)
     */
    private BigDecimal admixture;

    /**
     * 铝粉（kg）
     */
    private BigDecimal aluminitePowder;

    /**
     * 浇注温度（℃）
     */
    private BigDecimal teemTemporature;

    /**
     * 浇注稠度(cm)
     */
    private BigDecimal teemDense;

    /**
     * 外加水（kg)
     */
    private BigDecimal additionalWater;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除 : 1 未删除
2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}