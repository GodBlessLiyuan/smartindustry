package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import lombok.Data;

/**
 * sm_iqc_detect
 * @author 
 */
@Data
public class IqcDetectPO implements Serializable {
    private Long receiptBodyId;

    private String remark;

    /**
     * 1：允许良品
2：QE驳回重检验
3：未检验
     */
    private Byte status;

    private static final long serialVersionUID = 1L;
}