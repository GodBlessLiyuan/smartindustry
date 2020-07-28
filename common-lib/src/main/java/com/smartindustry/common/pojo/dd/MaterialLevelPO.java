package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_material_level
 * @author 
 */
@Data
public class MaterialLevelPO implements Serializable {
    private Long materialLevelId;

    private String materialLevelName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}