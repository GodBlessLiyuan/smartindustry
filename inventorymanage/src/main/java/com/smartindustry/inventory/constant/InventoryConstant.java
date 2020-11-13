package com.smartindustry.inventory.constant;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:16 2020/10/27
 * @version: 1.0.0
 * @description:
 */
public class InventoryConstant {
    /**
     * 正常/在库/-
     */
    public static final Byte STATUS_NORMAL= 1;

    /**
     * 锁定/作业中
     */
    public static final Byte STATUS_LOCK= 2;

    /**
     *  无
     */
    public static final String TEXT_NULL = "-";
    /**
     *  作业中
     */
    public static final String TEXT_WORKING = "作业中";

    /**
     * 满仓
     */
    public static final Byte STATUS_FULL = 3;

    /**
     * 满仓
     */
    public static final Byte STATUS_LACK = 2;

    /**
     * 正常
     */
    public static final Byte STATUS_ORDINARY = 1;
    /**
     * map 取值标识
     */
    public static final String VALUE_FLAG = "num";

}
