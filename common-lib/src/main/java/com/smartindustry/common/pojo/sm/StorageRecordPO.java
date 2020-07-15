package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * sm_storage_record
 *
 * @author
 */
@AllArgsConstructor
@Data
public class StorageRecordPO implements Serializable {
    private Long recordId;

    private Long receiptBodyId;

    private Long storageId;

    private Long userId;

    private String name;

    private String type;

    private Date createTime;

    /**
     * 1：录入标签
     * 5：IQC检测
     * 10：QE检测
     * 15：QE确认
     * 20：物料入库
     * 25：入库完成
     */
    private Byte status;

    private static final long serialVersionUID = 1L;

    public StorageRecordPO(Long receiptBodyId, Long userId, String name, String type, Byte status) {
        this(receiptBodyId, null, userId, name, type, status);
    }

    public StorageRecordPO(Long receiptBodyId, Long storageId, Long userId, String name, String type, Byte status) {
        this.receiptBodyId = receiptBodyId;
        this.storageId = storageId;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.createTime = new Date();
        this.status = status;
    }
}