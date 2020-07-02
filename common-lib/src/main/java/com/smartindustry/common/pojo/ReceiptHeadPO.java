package com.smartindustry.common.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * sm_receipt_head
 *
 * @author
 */
@Data
public class ReceiptHeadPO implements Serializable {
    private Long receiptHeadId;

    private String orderNo;

    /**
     * 1：PO单收料
     * 2：样品采购
     * 3：生产退料
     */
    private Byte orderType;

    private Date orderDate;

    private String supplier;

    private String buyer;

    private Date planDate;

    private String logisticsCompany;

    private String logisticsNo;

    /**
     * 1：到付
     * 2：寄付
     */
    private Byte receiptWay;

    private String remark;

    private Date createTime;

    private Date updateTime;

    /**
     * 1：未删除
     * 2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}