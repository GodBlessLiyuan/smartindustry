package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_material_type
 * @author 
 */
@Data
public class MaterialTypePO implements Serializable {
    private Long materialTypeId;

    private String materialTypeName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}