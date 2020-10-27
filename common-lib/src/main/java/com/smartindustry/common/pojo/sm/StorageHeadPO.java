package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
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
     * 入库时间
     */
    private Date storageTime;

    /**
     * 入库状态 : 1：已入库
2：待入库


     */
    private Byte status;

    /**
     * 备注
     */
    private String extra;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 : 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}