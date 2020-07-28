package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_warehouse_type
 * @author 
 */
@Data
public class WarehouseTypePO implements Serializable {
    private Long warehouseTypeId;

    private String warehouseTypeName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}