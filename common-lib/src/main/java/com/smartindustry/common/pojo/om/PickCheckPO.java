package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import lombok.Data;

/**
 * om_pick_check
 * @author 
 */
@Data
public class PickCheckPO implements Serializable {
    private Long pickHeadId;

    private String remark;

    /**
     * 1：同意
2：驳回-取消发货，退回仓库
3：待审核
4：驳回-等齐套发货
     */
    private Byte status;

    private static final long serialVersionUID = 1L;
}