package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_material_version
 * @author 
 */
@Data
public class MaterialVersionPO implements Serializable {
    private Long materialVersionId;

    private String materialVersionName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}