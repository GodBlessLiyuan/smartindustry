package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_supplier_record
 * @author 
 */
@Data
public class SupplierRecordPO implements Serializable {
    /**
     * 供应商记录ID
     */
    private Long supplierRecordId;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 操作人
     */
    private Long userId;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 操作名称
     */
    private String operationName;

    private static final long serialVersionUID = 1L;
}