package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_credit_level
 * @author 
 */
@Data
public class CreditLevelPO implements Serializable {
    private Long creditLevelId;

    private String creditLevelName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}