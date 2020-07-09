package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import lombok.Data;

/**
 * sm_qe_detect
 * @author 
 */
@Data
public class QeDetectPO implements Serializable {
    private Long receiptBodyId;

    /**
     * 1：未检验
2：允许良品
     */
    private Byte status;

    private static final long serialVersionUID = 1L;
}