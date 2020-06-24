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

    private String materialNo;

    private Byte materialType;

    private String materialDesc;

    private Integer orderTotal;

    private Integer acceptNum;

    private Date acceptDate;

    private Integer goodNum;

    private Integer badNum;

    private Integer stockNum;

    /**
     * 1��¼���ǩ
5��IQC���
10��QE���
15��QEȷ��
20���������
25��������
     */
    private Byte status;

    /**
     * 1��δɾ��
2����ɾ��
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}