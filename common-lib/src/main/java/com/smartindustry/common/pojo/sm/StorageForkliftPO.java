package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import lombok.Data;

/**
 * sm_storage_forklift
 * @author 
 */
@Data
public class StorageForkliftPO implements Serializable {
    /**
     * 入库叉车id
     */
    private Long storageForkliftId;

    /**
     * 入库单表头ID
     */
    private Long storageHeadId;

    /**
     * 叉车id
     */
    private Long forkliftId;

    /**
     * 当前运作的RFID
     */
    private String rfid;

    private static final long serialVersionUID = 1L;
}