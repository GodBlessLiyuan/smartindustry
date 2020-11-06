package com.smartindustry.common.pojo.ds;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * ds_sales_outbound_detail
 * @author 
 */
@Data
public class SalesOutboundDetailPO implements Serializable {
    /**
     * 销售出库明细ID
     */
    private Long salesOutboundDetailId;

    /**
     * 销售出库ID
     */
    private Long salesOutboundId;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 需求量
     */
    private BigDecimal needNum;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 金额
     */
    private BigDecimal totalPrice;

    private static final long serialVersionUID = 1L;
}