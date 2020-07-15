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

    private Long printLabelId;

    private String materialNo;

    private String packageId;

    private Integer storageNum;

    private Date storageTime;

    private static final long serialVersionUID = 1L;
}