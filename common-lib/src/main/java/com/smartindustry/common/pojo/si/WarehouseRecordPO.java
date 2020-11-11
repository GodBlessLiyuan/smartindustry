package com.smartindustry.common.pojo.si;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * si_warehouse_record
 * @author 
 */
@Data
public class WarehouseRecordPO implements Serializable {
    /**
     * 仓库记录表
     */
    private Long warehouseRecordId;

    /**
     * 仓库ID
     */
    private Long warehouseId;

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

    private static final long serialVersionUID = 1L;

    public WarehouseRecordPO(){}
    public WarehouseRecordPO(Long warehouseId,Long userId,String operationName ){
        this.warehouseId=warehouseId;
        this.userId=userId;
        this.createTime=new Date();
        this.operationName=operationName;
    }
}