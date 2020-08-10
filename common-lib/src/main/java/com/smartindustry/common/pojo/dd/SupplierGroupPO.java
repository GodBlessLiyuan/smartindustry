package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_supplier_group
 * @author 
 */
@Data
public class SupplierGroupPO implements Serializable {
    private Long supplierGroupId;

    private String supplierGroupName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}