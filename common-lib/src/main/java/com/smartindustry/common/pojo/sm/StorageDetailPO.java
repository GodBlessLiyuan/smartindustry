package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * sm_storage_detail
 * @author 
 */
@Data
public class StorageDetailPO implements Serializable {
    /**
     * 入库单ID
     */
    private Long storageId;

    /**
     * 入库单表头ID
     */
    private Long storageHeadId;

    /**
     * 库位ID
     */
    private Long locationId;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 入库数
     */
    private BigDecimal storageNum;

    /**
     * 入库时间
     */
    private Date storageTime;

    /**
     * 栈板RFID
     */
    private String rfid;

    /**
     * 入库状态 : 1 待入库
2 已入库
3 已出库
     */
    private Byte storageStatus;

    /**
     * 是否删除 1 未删除 2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}