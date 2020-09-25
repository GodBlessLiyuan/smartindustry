package com.smartindustry.common.pojo.wm;

import java.io.Serializable;
import lombok.Data;

/**
 * wm_work_bench
 * @author 
 */
@Data
public class WorkBenchPO implements Serializable {
    private Long workBenchId;

    private Long authorityId;

    private String benchName;

    /**
     * 1. 待办工作
2. 快捷入口
     */
    private Byte benchType;

    /**
     * 1. WMS
2. MES
3. ERP
4. MDM
     */
    private Byte benchModule;

    private String iconPath;

    private String urlPath;

    private static final long serialVersionUID = 1L;
}