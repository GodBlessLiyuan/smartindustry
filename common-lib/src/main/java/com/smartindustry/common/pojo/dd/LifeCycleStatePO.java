package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_life_cycle_state
 * @author 
 */
@Data
public class LifeCycleStatePO implements Serializable {
    private Long lifeCycleStateId;

    private String lifeCycleStateName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}