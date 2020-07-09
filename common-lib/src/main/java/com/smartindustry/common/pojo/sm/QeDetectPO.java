package com.smartindustry.common.pojo.sm;

import java.io.Serializable;

import lombok.Data;

/**
 * sm_qe_detect
 *
 * @author
 */
@Data
public class QeDetectPO implements Serializable {
    private Long receiptBodyId;

    private String remark;

    /**
     * 1：允许良品
     * 3：未检验
     * 4：特采
     * 5：退供应商
     */
    private Byte status;

    private static final long serialVersionUID = 1L;
}