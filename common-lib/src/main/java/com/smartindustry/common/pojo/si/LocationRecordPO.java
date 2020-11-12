package com.smartindustry.common.pojo.si;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * si_location_record
 * @author 
 */
@Data
public class LocationRecordPO implements Serializable {
    /**
     * 库位记录ID
     */
    private Long locationRecordId;

    /**
     * 库位ID
     */
    private Long locationId;

    /**
     * 操作人
     */
    private Long userId;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 操作名称
     */
    private String operationName;
    public LocationRecordPO(){}

    public LocationRecordPO(Long locationId,Long userId,String operationName){
        this.locationId=locationId;
        this.userId=userId;
        this.operationName=operationName;
    }
    private static final long serialVersionUID = 1L;
}