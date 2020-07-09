package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import lombok.Data;

/**
 * sm_storage_group
 * @author 
 */
@Data
public class StorageGroupPO implements Serializable {
    private Long storageGroupId;

    private Long storageId;

    private String locationNo;

    private static final long serialVersionUID = 1L;
}