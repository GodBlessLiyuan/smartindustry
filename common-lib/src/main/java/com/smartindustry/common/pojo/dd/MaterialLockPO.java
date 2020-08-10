package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_material_lock
 * @author 
 */
@Data
public class MaterialLockPO implements Serializable {
    private Long materialLockId;

    private String materialLockName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}