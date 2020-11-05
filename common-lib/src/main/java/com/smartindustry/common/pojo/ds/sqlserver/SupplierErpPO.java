package com.smartindustry.common.pojo.ds.sqlserver;

import java.io.Serializable;
import lombok.Data;

/**
 * 供应商
 * @author 
 */
@Data
public class SupplierErpPO implements Serializable {
    private Double supplierId;

    private String supplierCode;

    private String supplierName;

    private String address;

    private String telephone;

    private String cellphone;

    private String fax;

    private String homeAddress;

    private String email;

    private String contact;

    private String remark;

    private static final long serialVersionUID = 1L;
}