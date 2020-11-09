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

    /**
     * 是否在备货区：1.在成品区  2.在备料区
     */
    public static final Byte Preparation_NO = 1;
    public static final Byte Preparation_YES = 2;

    public static final String OPERATE_NAME_AGREE = "确认入库";
    public static final String OPERATE_NAME_INSERT = "创建入库单";
    public static final String OPERATE_NAME_EXECUTE = "执行入库";
    public static final String OPERATE_NAME_JOIN = "参与入库";
    public static final String OPERATE_NAME_FINISH = "完成入库";

    /**
     * 釜到栈运算
     */
    public static final Integer F2Z = 36;

    /**
     * 成品区的储位类型
     */
    public static final Long TYPE_FINISHED_AREA = 1L;
    /**
     * 备料区的储位类型
     */
    public static final Long TYPE_PREPARATION_AREA = 2L;


    /**
     * 采购出库单已出库
     */
    public static final Byte STATUS_OUTED = 1;
    /**
     * 采购出库单待出库
     */
    public static final Byte STATUS_OUTING = 3;

    /**
     * 生产入库
     */
    public static final Byte TYPE_PRODUCT_STORAGE = 2;
    /**
     * 备料区入库
     */
    public static final Byte TYPE_PRE_STORAGE = 3;


}
