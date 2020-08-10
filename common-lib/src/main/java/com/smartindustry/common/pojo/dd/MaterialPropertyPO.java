package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_material_property
 * @author 
 */
@Data
public class MaterialPropertyPO implements Serializable {
    private Long materialPropertyId;

    private String materialPropertyName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}