package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_material_record
 * @author 
 */
@Data
public class MaterialRecordPO implements Serializable {
    /**
     * 物料记录ID
     */
    private Long materialRecordId;

    /**
     * 物料ID
     */
    private Long materialId;

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