package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_lift_cycle_state
 * @author 
 */
@Data
public class LiftCycleStatePO implements Serializable {
    private Long liftCycleStateId;

    private String liftCycleStateName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}