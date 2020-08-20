package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * si_warehouse_record
 *
 * @author
 */
@NoArgsConstructor
@Data
public class WarehouseRecordPO implements Serializable {
    private Long warehouseRecordId;

    private Long warehouseId;

    private Long userId;

    private Date createTime;

    private String type;

    private static final long serialVersionUID = 1L;

    public WarehouseRecordPO(Long warehouseId, Long userId, String type) {
        this.warehouseId = warehouseId;
        this.userId = userId;
        this.createTime = new Date();
        this.type = type;
    }
}