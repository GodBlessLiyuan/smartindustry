package com.smartindustry.common.pojo.ds.sqlserver;

import java.io.Serializable;
import lombok.Data;

/**
 * 销售发货明细
 * @author 
 */
@Data
public class SaleDetailErpPO implements Serializable {
    private Double detailId;

    private Double saleId;

    private Double materialId;

    private String materialNo;

    private String materialName;

    private Double saleNum;

    private Double unitPrice;

    private Double sum;

    private String remark;

    private static final long serialVersionUID = 1L;
}