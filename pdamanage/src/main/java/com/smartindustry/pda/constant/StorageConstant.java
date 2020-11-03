package com.smartindustry.pda.constant;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:16 2020/10/27
 * @version: 1.0.0
 * @description:
 */
public class StorageConstant {
    /**
     * session imei
     */
    public final static String SESSION_IMEI = "imei";
    /**
     * session 出库表头IDs
     */
    public final static String SESSION_SHIDS = "shids";
    /**
     * session 出库表头ID
     */
    public final static String SESSION_SHID = "shid";
    /**
     * 已入库
     */
    public static final Byte STATUS_STORED = 1;
    /**
     * 待入库
     */
    public static final Byte STATUS_PRESTORED = 3;
    /**
     * 入库中
     */
    public static final Byte STATUS_STOREING = 2;
    /**
     * 由自己新增的出库单
     */
    public static final Byte TYPE_INSERT_BY_OURS = 1;

    public static final String OPERATE_NAME_AGREE = "确认入库";
    public static final String OPERATE_NAME_INSERT = "创建入库单";

}
