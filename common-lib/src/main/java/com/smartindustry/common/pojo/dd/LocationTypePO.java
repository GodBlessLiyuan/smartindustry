package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_location_type
 * @author 
 */
@Data
public class LocationTypePO implements Serializable {
    private Long locationTypeId;

    private String locationTypeName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}