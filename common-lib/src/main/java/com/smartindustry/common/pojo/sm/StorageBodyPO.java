package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sm_storage_body
 * @author 
 */
@Data
public class StorageBodyPO implements Serializable {
    /**
     * 入库单表体ID
     */
    private Long storageBodyId;

    /**
     * 入库单表头ID
     */
    private Long storageHeadId;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 库位ID
     */
    private Long locationId;

    /**
     * 车牌信息
     */
    private String carBrand;

    /**
     * 接受数量
     */
    private Integer acceptNum;

    /**
     * 接受日期
     */
    private Date acceptTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 : 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}