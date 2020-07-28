package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_warehouse
 * @author 
 */
@Data
public class WarehousePO implements Serializable {
    private Long warehouseId;

    private String warehouseNo;

    private String warehouseName;

    private Long warehouseTypeId;

    private String principal;

    private String phone;

    private String area;

    private String address;

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