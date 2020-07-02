package com.smartindustry.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 15:28
 * @description: TODO
 * @version: 1.0
 */
@Data
public class ReceiptBO extends ReceiptBodyBO {
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

    private Byte receiptWay;

    private String remark;

    private Byte qaStatus;
}
