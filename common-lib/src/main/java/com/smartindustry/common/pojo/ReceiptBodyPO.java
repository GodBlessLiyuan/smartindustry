package com.smartindustry.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sm_receipt_body
 * @author 
 */
@Data
public class ReceiptBodyPO implements Serializable {
    private Long receiptBodyId;

    private Long receiptHeadId;

    private String receiptNo;

    private String materialNum;

    private Byte materialType;

    private String materialDesc;

    private Integer orderTotal;

    private Integer acceptNum;

    private Date acceptDate;

    private Integer goodNum;

    private Integer badNum;

    private Integer stockNum;

    /**
     * 1：录入标签
5：IQC检测
10：QE检测
15：QE确认
20：入库
     */
    private Byte status;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}