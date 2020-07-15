package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import lombok.Data;

/**
 * om_pick_label
 * @author 
 */
@Data
public class PickLabelPO implements Serializable {
    private Long pickLabelId;

    private Long pickHeadId;

    private Long printLabelId;

    /**
     * 1：是
2：否
     */
    private Byte recommend;

    private static final long serialVersionUID = 1L;
}