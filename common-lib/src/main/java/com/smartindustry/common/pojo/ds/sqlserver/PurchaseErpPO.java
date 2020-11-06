package com.smartindustry.common.pojo.ds.sqlserver;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 采购到货
 * @author 
 */
@Data
public class PurchaseErpPO implements Serializable {
    private Double purchaseId;

    private String purchaseNo;

    private Double supplierId;

    private String orderNo;

    private Double orderId;

    private Double warehouseId;

    private Date acceptDate;

    private Double salesmanId;

    private String salesmanName;

    private Double operatorId;

    private String operatorName;

    private Double auditorId;

    private String auditorName;

    private Double payMethod;

    private String payName;

    private Double num;

    private String remark;

    private static final long serialVersionUID = 1L;
}