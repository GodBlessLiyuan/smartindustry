package com.smartindustry.workbench.constant;

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
     *  工作台类型- 待办工作
     */
    public static final Byte BENCH_TYPE_UNHANDLE = 1;

    /**
     *  工作台类型-快捷入口
     */
    public static final Byte BENCH_TYPE_SHORTCUT = 2;

    /**
     * 工作台模块 -WMS
     */
    public static final Byte BENCH_MODULE_WMS = 1;
    /**
     * 工作台模块 -MES
     */
    public static final Byte BENCH_MODULE_MES = 2;

    /**
     * 工作台模块 -ERP
     */
    public static final Byte BENCH_MODULE_ERP = 3;

    /**
     * 工作台模块 -MDM
     */
    public static final Byte BENCH_MODULE_MDM = 4;



    /**
     * IQC待确认
     */
    public static final Byte WORK_IQC_WAIT_CHECK=1;
    /**
     * QE待确认
     */
    public static final Byte WORK_QE_WAIT_CHECK=2;

    /**
     * QE待确认
     */
    public static final Byte WORK_QE_WAIT_CONFIRM=3;

    /**
     * 待入库
     */
    public static final Byte WORK_WAIT_STORAGE=4;

    /**
     * 待拣货
     */
    public static final Byte WORK_WAIT_PICK=5;

    /**
     * OQC待确认
     */
    public static final Byte WORK_OQC_WAIT_CHECK=6;

    /**
     * 工单待审核
     */
    public static final Byte WORK_ORDER_WAIT_AUDIT=7;

    /**
     * 待出库
     */
    public static final Byte WORK_WAIT_OUTBOUND=8;


    /**
     * 入库单状态
     */
    public static final Byte RECEIPT_STATUS_QE_CHECK=10;

    public static final Byte RECEIPT_STATUS_QE_CONFIRM = 15;

    public static final Byte RECEIPT_STATUS_IQC_CHECK=5;

    public static final Byte QUALITY_UNCHECK= 3;

    public static final Byte STORAGE_STATUS_WAIT=3;

    /**
     * 物料拣货状态之未处理
     */
    public static final Byte PICK_HEAD_STATUS_UNHANDLE=5;

    /**
     * 工单审核/OQCj检验
     */
    public static final Byte PICK_HEAD_AUDIT_OQC=15;

    public static final Integer NUM_1 = 1;

    public static final Byte PICK_HEAD_SOURCE_ORDER=1;

    public static final Byte PICK_HEAD_SOURCE_SAIL = 2;

    /**
     * 待出库
     */
    public static final Byte OUT_BOUND_STATUS_WAIT = 3;

}
