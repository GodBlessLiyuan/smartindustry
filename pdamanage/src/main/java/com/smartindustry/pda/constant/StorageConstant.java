package com.smartindustry.pda.constant;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:16 2020/10/27
 * @version: 1.0.0
 * @description:
 */
public class StorageConstant {
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


    public static final String OPERATE_NAME_AGREE = "确认入库";
    public static final String OPERATE_NAME_INSERT = "创建入库单";
    public static final String OPERATE_NAME_EXECUTE = "执行入库";
    public static final String OPERATE_NAME_JOIN = "参与入库";

    /**
     * 釜到栈运算
     */
    public static final Integer F2Z = 36;

}
