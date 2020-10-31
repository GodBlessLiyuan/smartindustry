package com.smartindustry.common.pojo.sm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * sm_storage_body
 * @author 
 */
@Data
public class StorageBodyPO implements Serializable {
    private Long storageBodyId;

    private Long storageHeadId;

    private Long materialId;

    private Long locationId;

    private String carBrand;

    private BigDecimal expectNum;

    private BigDecimal storageNum;

    private Date acceptTime;

    private Date createTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}