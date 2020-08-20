package com.smartindustry.common.pojo.si;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * si_supplier_record
 *
 * @author
 */
@NoArgsConstructor
@Data
public class SupplierRecordPO implements Serializable {
    private Long supplierRecordId;

    private Long supplierId;

    private Long userId;

    private Date createTime;

    private String type;

    private static final long serialVersionUID = 1L;

    public SupplierRecordPO(Long supplierId, Long userId, String type) {
        this.supplierId = supplierId;
        this.userId = userId;
        this.createTime = new Date();
        this.type = type;
    }
}