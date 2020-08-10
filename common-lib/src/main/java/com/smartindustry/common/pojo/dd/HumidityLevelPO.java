package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_humidity_level
 * @author 
 */
@Data
public class HumidityLevelPO implements Serializable {
    private Long humidityLevelId;

    private String humidityLevelName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}