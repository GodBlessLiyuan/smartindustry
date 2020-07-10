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
    private String locationNo;

    private String locationCode;

    private String locationName;

    private Byte locationType;

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