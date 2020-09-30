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
    public static final String RECORD_CANCEL_DELIVERY = "取消发货,退回仓库";
    public static final String RECORD_WAIT_DELIVERY = "等齐套发货";
    public static final String RECORD_AGREE = "审核-同意";
    public static final String RECORD_ADD = "新增";
    public static final String RECORD_SUBMIT = "提交";
    public static final String RECORD_CONFIRM_OUTBOUND = "确认出库";
    public static final String RECORD_DISAGREE = "审核-驳回";
    public static final String RECORD_OUTBOUND_ORDER = "形成审核单";
    public static final String RECORD_AGREE_OUT = "同意出库";
    public static final String RECORD_OQC = "OQC报检";

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

    public static final Byte WORK_ORDER_OUTBOUND = 20;

    /**
     * 来源订单类型
     * 1：工单
     * 2：销售
     * 3：调拨
     */
    public static final Byte TYPE_PICK = 1;
    public static final Byte TYPE_SALE = 2;
    public static final Byte TYPE_TRANSFER = 3;

    /**
     * 插入到入库单的来源订单类型
     */
    public static final Byte TYPE_TRANSFER_INSERT = 2;
    /**
     * 物料入库
     */
    //  已入库
    public static final Byte MATERIAL_STORAGE_FINISH = 1;
    //  入库中
    public static final Byte MATERIAL_STORAGE_BEING = 2;
    //  待入库
    public static final Byte MATERIAL_STORAGE_PENDING = 3;

    /**
     * 生成入库单的操作记录
     */
    public static final String RECORD_TYPE_STORAGE_INVOICE = "形成入库单";
    public static final String RECORD_TYPE_STORAGE_CONFIRM = "确认入库";

    //  物料入库
    public static final Byte RECEIPT_MATERIAL_STORAGE = 20;

    /**
     * 销售、采购、生产PID强关联 - KEY
     */
    public static final String K_PID_RELATE = "pid_relate";
    /**
     * 是
     */
    public static final String V_YES = "Y";
    /**
     * 否
     */
    public static final String V_NO = "N";

    /**
     * 工单出库
     */
    public static final Byte TYPE_OUT_WORK = 1;
    /**
     * 销售出库
     */
    public static final Byte TYPE_OUT_SHIP = 2;
    /**
     * 其他出库
     */
    public static final Byte TYPE_OUT_OTHER= 3;
}
