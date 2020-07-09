package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import lombok.Data;

/**
 * sm_qe_confirm
 * @author 
 */
@Data
public class QeConfirmPO implements Serializable {
    private Long receiptBodyId;

    private String remark;

    /**
     * 1：不良，待确认
2：特采
3：退供应商
     */
    private Byte status;

    private static final long serialVersionUID = 1L;
}