package com.smartindustry.common.pojo.am;

import java.io.Serializable;
import lombok.Data;

/**
 * am_role_stage
 * @author 
 */
@Data
public class RoleStagePO implements Serializable {
    private Long stageId;

    private Long authorityId;

    private String stageName;

    /**
     * 1. 待办工作
2. 快捷入口
     */
    private Byte stageType;

    /**
     * 1. WMS
2. MES
3. ERP
4. MDM
     */
    private Byte stageModule;

    private String iconPath;

    private String urlPath;

    private static final long serialVersionUID = 1L;
}