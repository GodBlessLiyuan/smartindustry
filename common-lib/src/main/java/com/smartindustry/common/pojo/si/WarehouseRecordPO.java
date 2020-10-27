package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
}