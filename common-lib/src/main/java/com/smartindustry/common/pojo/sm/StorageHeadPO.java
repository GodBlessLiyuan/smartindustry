package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * sm_storage_head
 * @author 
 */
@Data
public class StorageHeadPO implements Serializable {
    /**
     * 入库单表头ID
     */
    private Long storageHeadId;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 入库单编号
     */
    private String storageNo;

    /**
     * 来源单号
     */
    private String sourceNo;

    /**
     * 来源类型 : 1 原材料入库
2 生产入库
3 备料区入库
     */
    private Byte sourceType;

    /**
     * 入库时间
     */
    private Date storageTime;

    /**
     * 期望入库数
     */
    private BigDecimal expectNum;

    /**
     * 已入库数量
     */
    private BigDecimal storageNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 入库状态 : 1：已入库
2：入库中
3：待入库


     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 : 1：未删除
2：已删除
     */
    private Byte dr;

    /**
     * 付款方式 1. 现金 2. 欠款
     */
    private Byte payMethod;

    /**
     * 供应商ID
     */
    private Long supplierId;

    private static final long serialVersionUID = 1L;
}