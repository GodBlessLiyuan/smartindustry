package com.smartindustry.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sm_storage_location
 * @author 
 */
@Data
public class StorageLocationPO implements Serializable {
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