package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * sm_storage_detail
 * @author 
 */
@Data
public class StorageDetailPO implements Serializable {
    private Long storageId;

    private Long storageBodyId;

    private Long locationId;

    private BigDecimal storageNum;

    private Date storageTime;

    private String rfid;

    private static final long serialVersionUID = 1L;
}