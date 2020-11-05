package com.smartindustry.common.pojo.ds.sqlserver;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 销售发货
 * @author 
 */
@Data
public class SaleOutboundErpPO implements Serializable {
    private Double saleId;

    private String saleNo;

    private Double clientId;

    private Date saleDate;

    private Double operatorId;

    private String operatorName;

    private static final long serialVersionUID = 1L;
}