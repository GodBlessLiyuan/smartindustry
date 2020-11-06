package com.smartindustry.common.pojo.ds;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ds_sales_outbound
 * @author 
 */
@Data
public class SalesOutboundPO implements Serializable {
    /**
     * 销售出库ID
     */
    private Long salesOutboundId;

    /**
     * 业务单号
     */
    private String salesNo;

    /**
     * 开票日期
     */
    private Date salesDate;

    /**
     * 客户id
     */
    private Long clientId;

    /**
     * 开票员ID
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}