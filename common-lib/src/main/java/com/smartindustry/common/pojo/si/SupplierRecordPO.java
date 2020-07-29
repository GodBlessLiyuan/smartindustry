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
    private Long supplierRecordId;

    private Long supplierId;

    private Long userId;

    private Date createTime;

    private String type;

    private static final long serialVersionUID = 1L;
}