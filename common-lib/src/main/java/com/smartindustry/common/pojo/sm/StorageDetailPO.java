package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import lombok.Data;

/**
 * sm_storage_detail
 * @author 
 */
@Data
public class StorageDetailPO implements Serializable {
    private Long storageDetailId;

    private Long storageGroupId;

    private Long printLabelId;

    private static final long serialVersionUID = 1L;
}