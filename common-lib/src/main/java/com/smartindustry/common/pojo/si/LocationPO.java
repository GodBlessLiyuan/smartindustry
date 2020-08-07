package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_location
 * @author 
 */
@Data
public class LocationPO implements Serializable {
    private Long locationId;

    private String locationNo;

    private String locationName;

    private Long locationTypeId;

    private Long warehouseId;

    private String remark;

    private Long userId;

    private Date createTime;

    private Date updateTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}