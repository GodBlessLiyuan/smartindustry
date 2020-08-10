package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_measure_unit
 * @author 
 */
@Data
public class MeasureUnitPO implements Serializable {
    private Long measureUnitId;

    private String measureUnitName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}