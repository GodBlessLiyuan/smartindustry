package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import lombok.Data;

/**
 * sm_receipt_label
 * @author 
 */
@Data
public class ReceiptLabelPO implements Serializable {
    private Long receiptLabelId;

    private Long printLabelId;

    private Long receiptBodyId;

    private Long storageId;

    private static final long serialVersionUID = 1L;
}