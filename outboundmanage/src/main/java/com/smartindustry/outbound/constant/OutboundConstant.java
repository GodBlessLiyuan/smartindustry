package com.smartindustry.outbound.constant;

/**
 * @author: xiahui
 * @date: Created in 2020/7/15 15:58
 * @description: 出库常量
 * @version: 1.0
 */
public class OutboundConstant {
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
    public static final Byte OUTBOUND_STATUS_ALL = 1;
    /**
     * 欠料出库
     */
    public static final Byte OUTBOUND_STATUS_LACK = 2;
    /**
     * 未出库
     */
    public static final Byte OUTBOUND_STATUS_WAIT = 3;
}
