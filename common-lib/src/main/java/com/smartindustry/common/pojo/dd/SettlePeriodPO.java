package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_settle_period
 * @author 
 */
@Data
public class SettlePeriodPO implements Serializable {
    private Long settlePeriodId;

    private String settlePeriodName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}