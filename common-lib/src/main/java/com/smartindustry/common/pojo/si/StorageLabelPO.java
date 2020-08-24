package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_storage_label
 * @author 
 */
@Data
public class StorageLabelPO implements Serializable {
    private Long storageLabelId;

    private Long printLabelId;

    private Long materialId;

    private Long locationId;

    private String packageId;

    private String sourceNo;

    /**
     * 1：PO单收料
2：样品采购
3：生产退料
     */
    private Byte sourceType;

    /**
     * 1：良品
2：非良品
     */
    private Byte type;

    private Integer storageNum;

    private Date storageTime;

    private Long materialLockId;

    /**
     * 1：在库
11：调拨入库
14：生产入库
20：工单出库
21：调拨出库
23：销售出库
     */
    private Byte status;

    private static final long serialVersionUID = 1L;
}