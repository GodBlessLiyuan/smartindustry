package com.smartindustry.common.pojo.ds.sqlserver;

import java.io.Serializable;
import lombok.Data;

/**
 * 采购到货明细
 * @author 
 */
@Data
public class PurchaseDetailErpPO implements Serializable {
    private Double detailId;

    private Double purchaseId;

    private Double materialId;

    private String materialName;

    private String materialNo;

    private Double num;

    private Double unitPrice;

    private Double sum;

    private Double unitPriceNoTax;

    private Double sumNoTax;

    private String remark;

    private static final long serialVersionUID = 1L;
}