package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_material_unit
 * @author 
 */
@Data
public class MaterialUnitPO implements Serializable {
    private Long materialUnitId;

    private String materialUnitName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}