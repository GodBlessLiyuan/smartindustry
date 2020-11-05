package com.smartindustry.outbound.constant;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:16 2020/10/27
 * @version: 1.0.0
 * @description:
 */
public class OutboundConstant {
    /**
     * 采购入库单已入库
     */
    public static final Byte STATUS_STORED = 1;
    /**
     * 采购入库单待入库
     */
    public static final Byte STATUS_STOREING = 3;

    /**
     * 采购出库单已出库
     */
    public static final Byte STATUS_OUTED = 1;
    /**
     * 采购出库单待出库
     */
    public static final Byte STATUS_OUTING = 3;

    /**
     * 由自己新增的出库单
     */
    public static final Byte TYPE_INSERT_BY_OURS = 1;
    /**
     * 浇注而成自动生成的出库单
     */
    public static final Byte TYPE_INSERT_BY_AUTO = 2;

    public static final String OPERATE_NAME_AGREE = "确认入库";
    public static final String OPERATE_NAME_INSERT = "形成入库单";

    public static final String OPERATE_NAME_AGREE_OUT = "确认出库";
    public static final String OPERATE_NAME_INSERT_OUT = "形成出库单";
}
