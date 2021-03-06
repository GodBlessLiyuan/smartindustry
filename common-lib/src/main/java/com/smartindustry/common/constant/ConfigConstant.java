package com.smartindustry.common.constant;

import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 18:37
 * @description: 配置常量
 * @version: 1.0
 */
public class ConfigConstant {

    /**
     * 销售、采购、生产PID强关联 - KEY
     */
    public static final String K_PID_RELATE = "pid_relate";

    /**
     * 入库质检 - KEY
     */
    public static final String K_STORAGE_QUALITY = "storage_quality";
    /**
     * 出库质检 - KEY
     */
    public static final String K_OUTBOUND_QUALITY_KEY = "outbound_quality";

    /**
     * 是
     */
    public static final String V_YES = "Y";
    /**
     * 否
     */
    public static final String V_NO = "N";

    /**
     * 自动生成备料区入库单的时间边界
     */
    public static final String TIME_BOUNDARY_ONE = "07:30:00";
    public static final String TIME_BOUNDARY_TWO = "19:30:00";
}
