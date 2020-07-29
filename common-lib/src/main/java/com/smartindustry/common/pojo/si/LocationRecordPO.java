package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_location_record
 * @author 
 */
@Data
public class LocationRecordPO implements Serializable {
    private Long locationRecordId;

    private Long locationId;

    private Long userId;

    private Date createTime;

    private String type;

    private static final long serialVersionUID = 1L;
}