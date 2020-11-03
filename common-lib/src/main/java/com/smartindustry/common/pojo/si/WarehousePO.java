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
    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 仓库编号
     */
    private String warehouseNo;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 仓库类型ID
     */
    private Long warehouseTypeId;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 地区
     */
    private String area;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 : 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}