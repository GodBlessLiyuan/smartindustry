package com.smartindustry.workbench.constants;

/**
 * @author hui.feng
 * @date created in 2020/9/27
 * @description
 */
public class WorkBenchConstant {

    /**
     * 下面常量均为wm_work_bench type为1时的常量
     */

    /**
     * IQC待确认
     */
    public static final Long WORK_IQC_WAIT_CHECK=1L;
    /**
     * QE待确认
     */
    public static final Long WORK_QE_WAIT_CHECK=2L;

    /**
     * QE待确认
     */
    public static final Long WORK_QE_WAIT_CONFIRM=3L;

    /**
     * 待入库
     */
    public static final Long WORK_WAIT_STORAGE=4L;

    /**
     * 待拣货
     */
    public static final Long WORK_WAIT_PICK=5L;

    /**
     * OQC待确认
     */
    public static final Long WORK_OQC_WAIT_CHECK=6L;

    /**
     * 工单待审核
     */
    public static final Long WORK_ORDER_WAIT_AUDIT=7L;

    /**
     * 待出库
     */
    public static final Long WORK_WAIT_OUTBOUND=8L;

}
