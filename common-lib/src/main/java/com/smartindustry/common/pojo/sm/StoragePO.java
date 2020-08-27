package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * sm_storage
 *
 * @author
 */
@Data
public class StoragePO implements Serializable {
    private Long storageId;

    private String storageNo;

    private String sourceNo;

    /**
     * 1：收料单
     * 2：其他入库单
     */
    private Byte sourceType;

    private Integer pendingNum;

    private Integer storedNum;

    private Date storageTime;

    /**
     * 1：已入库
     * 2：入库中
     * 3：待入库
     */
    private Byte status;

    /**
     * 1：良品
     * 2：非良品
     */
    private Byte type;

    private Date createTime;

    /**
     * 1：未删除
     * 2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}