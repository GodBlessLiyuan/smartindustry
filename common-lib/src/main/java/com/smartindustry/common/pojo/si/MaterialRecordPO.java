package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * si_material_record
 *
 * @author
 */
@Data
public class MaterialRecordPO implements Serializable {
    private Long materialRecordId;

    private Long materialId;

    private Long userId;

    private Date createTime;

    private String type;

    private static final long serialVersionUID = 1L;

    public MaterialRecordPO(Long materialId, Long userId, String type) {
        this.materialId = materialId;
        this.userId = userId;
        this.createTime = new Date();
        this.type = type;
    }
}