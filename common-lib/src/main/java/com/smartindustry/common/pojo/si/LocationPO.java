package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * si_location
 * @author 
 */
@Data
public class LocationPO implements Serializable {
    /**
     * 库位ID
     */
    private Long locationId;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 货位类型ID
     */
    private Long locationTypeId;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 库位编号
     */
    private String locationNo;

    /**
     * 库位名称
     */
    private String locationName;

    /**
     * 可容纳托盘数
     */
    private BigDecimal holdTrayNum;

    /**
     * 现存数量 : 现存数量
     */
    private BigDecimal existNum;

    /**
     * 备注
     */
    private String remark;

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