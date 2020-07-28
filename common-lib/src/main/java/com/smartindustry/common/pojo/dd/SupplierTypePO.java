package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_supplier_type
 * @author 
 */
@Data
public class SupplierTypePO implements Serializable {
    private Long supplierTypeId;

    private String supplierTypeName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}