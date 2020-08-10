package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_supplier
 * @author 
 */
@Data
public class SupplierPO implements Serializable {
    private Long supplierId;

    private String supplierNo;

    private Long supplierGroupId;

    private Long certStatusId;

    private Long supplierTypeId;

    private Long settlePeriodId;

    private Long currencyId;

    private String supplierName;

    private String contactName;

    private String phone;

    private String fax;

    private String site;

    private String mail;

    private String area;

    private String address;

    private String remark;

    private Long userId;

    private Date createTime;

    private Date updateTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}