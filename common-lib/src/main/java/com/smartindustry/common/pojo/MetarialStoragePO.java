package com.smartindustry.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sm_metarial_storage
 * @author 
 */
@Data
public class MetarialStoragePO implements Serializable {
    private Long storageId;

    private Long receiptBodyId;

    private Long storageNo;

    private Date createTime;

    /**
     * 1：待入库
2：入库中
3：已入库


     */
    private Byte status;

    private Integer pendingNum;

    private Integer storedNum;

    private Date storageTime;

    /**
     * 1：良品
2：非良品
     */
    private Byte type;

    private static final long serialVersionUID = 1L;
}