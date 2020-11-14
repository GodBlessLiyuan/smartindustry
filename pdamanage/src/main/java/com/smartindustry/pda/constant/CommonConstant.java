package com.smartindustry.pda.constant;

/**
 * @author: xiahui
 * @date: Created in 2020/11/4 18:39
 * @description: 公共模块常量
 * @version: 1.0
 */
public class CommonConstant {
    public static final String[] COLORS = new String[]{"#FF0000", "#00FF00", "#0000FF"};
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
     * 叉车-入库(原材料）
     */
    public final static Byte FORKLIFT_STORAGE_START_RAW = 1;
    /**
     * 叉车-入库(备料）
     */
    public final static Byte FORKLIFT_STORAGE_START_PREPARE = 2;
    /**
     * 叉车-入库(成品）
     */
    public final static Byte FORKLIFT_STORAGE_START_PRODUCT = 3;

    /**
     * 叉车-出库开始
     */
    public final static Byte FORKLIFT_WORK_OUTBOUND_START = 11;
    /**
     * 叉车-出库结束
     */
    public final static Byte FORKLIFT_WORK_OUTBOUND_END = 12;

    /**
     * 在库
     */
    public final static Byte STATUS_RFID_STORAGE = 1;
    /**
     * 销售出库
     */
    public final static Byte STATUS_RFID_OUTBOUND_SALE = 2;
    /**
     * 待入库
     */
    public final static Byte STATUS_RFID_PRE_STORAGE = 3;
    /**
     * 已出库
     */
    public final static Byte STATUS_RFID_OUTBOUND_DONE = 4;

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
    /**
     * 消息消失
     */
    public static final Byte TYPE_TITLE_VANISH = 5;


    /**
     * 无效
     */
    public static final Byte RFID_INVALID = 0;

    /**
     * 入库开始(原材料)
     */
    public static final Byte RFID_STORAGE_START_RAW = 1;
    /**
     * 入库开始(备料)
     */
    public static final Byte RFID_STORAGE_START_PREPARE = 2;
    /**
     * 入库开始(成品)
     */
    public static final Byte RFID_STORAGE_START_PRODUCT = 3;
    /**
     * 入库警告（原材料入原材料）
     */
    public static final Byte RFID_STORAGE_WARN_RAW_RAW = 4;
    /**
     * 入库完成(原材料入成品）
     */
    public static final Byte RFID_STORAGE_END_RAW_PRODUCT = 5;
    /**
     * 入库完成(原材料入备料）
     */
    public static final Byte RFID_STORAGE_END_RAW_PREPARE = 6;
    /**
     * 入库完成(备料入成品）
     */
    public static final Byte RFID_STORAGE_END_PREPARE_PRODUCT = 7;
    /**
     * 入库完成(成品入成品）
     */
    public static final Byte RFID_STORAGE_END_PRODUCT_PRODUCT = 8;
    /**
     * 入库完成(成品入备料）
     */
    public static final Byte RFID_STORAGE_END_PRODUCT_PREPARE = 9;

    /**
     * 出库开始
     */
    public static final Byte RFID_OUTBOUND_START = 11;
    /**
     * 出库 - 物归原主
     */
    public static final Byte RFID_OUTBOUND_RETURN = 12;
    /**
     * 出库完成
     */
    public static final Byte RFID_OUTBOUND_END = 13;
    /**
     * 库位类型：备料区：1
     */
    public static final Byte LOCATION_TYPE_PREPARE = 1;
    /**
     * 库位类型：成品区：2
     */
    public static final Byte LOCATION_TYPE_PRODUCT = 2;
}
