package com.smartindustry.common.pojo.pda;

import java.io.Serializable;
import lombok.Data;

/**
 * pda_storage_forklift
 * @author 
 */
@Data
public class StorageForkliftPO implements Serializable {
    private Long storageForkliftId;

    private Long storageHeadId;

    private Long forkliftId;

    private String rfid;

    private static final long serialVersionUID = 1L;
}