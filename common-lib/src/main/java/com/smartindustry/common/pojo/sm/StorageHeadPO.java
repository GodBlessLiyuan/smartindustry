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
    private Long storageHeadId;

    private Long warehouseId;

    private String storageNo;

    private String sourceNo;

    /**
     *  
     */
    private Byte sourceType;

    private Date storageTime;

    private BigDecimal expectNum;

    private BigDecimal storageNum;

    private String extra;

    /**
     * 1：已入库
2：入库中
3：待入库


     */
    private Byte status;

    private Date createTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}