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
    private Long storageMaterialId;

    private String locationNo;

    private String materialNo;

    private Long printLabelId;

    private String packageId;

    private String orderNo;

    /**
     * 1：PO单收料
2：样品采购
3：生产退料
     */
    private Byte orderType;

    private Integer storageNum;

    private Date storageTime;

    private static final long serialVersionUID = 1L;
}