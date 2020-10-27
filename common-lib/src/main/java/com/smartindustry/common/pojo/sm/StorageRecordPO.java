package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sm_storage_record
 * @author 
 */
@Data
public class StorageRecordPO implements Serializable {
    /**
     * 操作记录
     */
    private Long recordId;

    /**
     * 入库单表头ID
     */
    private Long storageHeadId;

    /**
     * 操作人ID
     */
    private Long userId;

    /**
     * 操作名称
     */
    private String operationName;

    /**
     * 操作时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}