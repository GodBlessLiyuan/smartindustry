package com.smartindustry.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sm_material_storage
 * @author 
 */
@Data
public class MaterialStoragePO implements Serializable {
    private Long storageId;

    private Long receiptBodyId;

    private String storageNo;

    private Integer pendingNum;

    private Integer storedNum;

    private Date storageTime;

    /**
     * 1：已入库
3：待入库


     */
    private Byte status;

    /**
     * 1：良品
2：非良品
     */
    private Byte type;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}