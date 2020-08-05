package com.smartindustry.outbound.constant;

/**
 * @author: xiahui
 * @date: Created in 2020/7/15 15:58
 * @description: 出库常量
 * @version: 1.0
 */
public class OutboundConstant {
    public static final String FILE_LOGISTICS = "logistics";

    /**
     * 待推荐
     */
    public static final Byte MATERIAL_STATUS_NOT_RECOMMEND = 1;
    /**
     * 未处理
     */
    public static final Byte MATERIAL_STATUS_UNPROCESSED = 5;
    /**
     * 物料拣货
     */
    public static final Byte MATERIAL_STATUS_PICK = 10;
    /**
     * 工单审核|OQC检验
     */
    public static final Byte MATERIAL_STATUS_CHECK = 15;
    /**
     * 等齐套发货
     */
    public static final Byte MATERIAL_STATUS_WAIT = 20;
    /**
     * 取消发货，退货仓库
     */
    public static final Byte MATERIAL_STATUS_RETURN = 25;
    /**
     * 物料出库
     */
    public static final Byte MATERIAL_STATUS_STORAGE = 30;
    /**
     * 完成出库
     */
    public static final Byte MATERIAL_STATUS_FINISH = 35;
    /**
     * 确认出库
     */
    public static final Byte MATERIAL_STATUS_CONFIRM = 40;

    /**
     * 全部出库
     */
    public static final Byte PICK_OUTBOUND_ALL = 1;
    /**
     * 欠料出库
     */
    public static final Byte PICK_OUTBOUND_LACK = 2;
    /**
     * 未出库
     */
    public static final Byte PICK_OUTBOUND_WAIT = 3;

    /**
     * 已出库
     */
    public static final Byte OUTBOUND_STATUS_FINISH = 1;
    /**
     * 未出库
     */
    public static final Byte OUTBOUND_STATUS_WAIT = 3;

    /**
     * 操作人的操作文案
     */
    public static final String RECORD_CANCEL_DELIVERY = "取消发货";
    public static final String RECORD_WAIT_DELIVERY = "等齐料发货";
    public static final String RECORD_AGREE = "同意";
    public static final String RECORD_ADD = "新增";
    public static final String RECORD_CONFIRM_OUTBOUND = "确认出库";

    public static final String RECORD_OUTBOUND_ORDER = "形成审核单";

    /**
     * OQC审核的同意
     */
    public static final Byte AGREE = 1;
    /**
     * OQC审核的驳回-取消发货，退回仓库
     */
    public static final Byte TURN_DOWN_CANCEL = 2;
    /**
     * OQC审核的待审核
     */
    public static final Byte PENDING = 3;
    /**
     * OQC审核的驳回，等齐套发货
     */
    public static final Byte PENDING_WAIT = 4;



}
