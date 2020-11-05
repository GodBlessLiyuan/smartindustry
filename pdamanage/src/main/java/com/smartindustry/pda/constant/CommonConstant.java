package com.smartindustry.pda.constant;

/**
 * @author: xiahui
 * @date: Created in 2020/11/4 18:39
 * @description: 公共模块常量
 * @version: 1.0
 */
public class CommonConstant {
    /**
     * session imei
     */
    public final static String SESSION_IMEI = "imei";
    /**
     * session 入库单表头ID
     */
    public final static String SESSION_SHID = "shid";
    /**
     * session 出库表头ID
     */
    public final static String SESSION_OHID = "ohid";
    /**
     * session 库位RFID状态：1-入库；2-出库；
     */
    public final static String SESSION_STATUS_FORKLIFT = "forklift_status";
    /**
     * session 物料RFID
     */
    public final static String SESSION_MRFID = "mrfid";
    /**
     * session 库位RFID
     */
    public final static String SESSION_LRFID = "lrfid";

    /**
     * 待执行
     */
    public final static Byte TYPE_LIST_TODO = 1;
    /**
     * 执行中
     */
    public final static Byte TYPE_LIST_DOING = 2;
    /**
     * 已执行
     */
    public final static Byte TYPE_LIST_DONE = 3;
    /**
     * 未完成
     */
    public final static Byte TYPE_LIST_UNDONE = 4;

    /**
     * 入库标志
     */
    public final static Byte FLAG_STORAGE = 1;
    /**
     * 出库标志
     */
    public final static Byte FLAG_OUTBOUND = 2;

    /**
     * 叉车-空闲
     */
    public final static Byte STATUS_FORKLIFT_IDLE = 1;
    /**
     * 叉车-忙碌
     */
    public final static Byte STATUS_FORKLIFT_BUSY = 2;
    /**
     * 叉车-不在线
     */
    public final static Byte STATUS_FORKLIFT_OFFLINE = 3;

    /**
     * 叉车-入库
     */
    public final static Byte STATUS_FORKLIFT_WORK_STORAGE = 1;
    /**
     * 叉车-出库
     */
    public final static Byte STATUS_FORKLIFT_WORK_OUTBOUND_ONE = 2;
    /**
     * 叉车-出库
     */
    public final static Byte STATUS_FORKLIFT_WORK_OUTBOUND_TWO = 3;

    /**
     * 入库
     */
    public final static Byte STATUS_RFID_STORAGE = 1;
    /**
     * 销售出库
     */
    public final static Byte STATUS_RFID_OUTBOUND = 2;
    /**
     * 待入库
     */
    public final static Byte STATUS_RFID_PRE_STORAGE = 3;


    /**
     * 无效
     */
    public static final Byte STATUS_FORKLIFT_RFID_NONE = 0;
    /**
     * 入库 - 叉起物料
     */
    public static final Byte STATUS_FORKLIFT_RFID_STORAGE_FORKLIFT = 1;
    /**
     * 入库
     */
    public static final Byte STATUS_FORKLIFT_RFID_STORAGE_DONE = 2;
    /**
     * 出库 - 叉起物料
     */
    public static final Byte STATUS_FORKLIFT_RFID_OUTBOUND_FORKLIFT = 4;
    /**
     * 出库
     */
    public static final Byte STATUS_FORKLIFT_RFID_OUTBOUND_DONE = 5;

    /**
     * 提示信息
     */
    public static final Byte TYPE_TITLE_INTO = 1;
    /**
     * 警告信息
     */
    public static final Byte TYPE_TITLE_WARN = 2;
    /**
     * 错误信息
     */
    public static final Byte TYPE_TITLE_ERROR = 3;
    /**
     * 弹框信息
     */
    public static final Byte TYPE_TITLE_BOX = 4;
}
