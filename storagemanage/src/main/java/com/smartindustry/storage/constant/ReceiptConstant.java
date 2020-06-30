package com.smartindustry.storage.constant;

/**
 * @author: xiahui
 * @date: Created in 2020/6/30 15:21
 * @description: 收料单 常量
 * @version: 1.0
 */
public class ReceiptConstant {

    /**
     * 收料单 状态
     */
    //  录入标签
    public static final Byte RECEIPT_ENTRY_LABEL = 1;
    //  IQC检测
    public static final Byte RECEIPT_IQC_DETECT = 5;
    //  QE检测
    public static final Byte RECEIPT_QE_DETECT = 10;
    //  QE确认
    public static final Byte RECEIPT_QE_CONFIRM = 15;
    //  物料入库
    public static final Byte RECEIPT_MATERIAL_STORAGE = 20;
    //  入库完成
    public static final Byte RECEIPT_STORAGE_FINISH = 25;

    /**
     * IQC检验 状态
     */
    //  允许良品
    public static final Byte IQC_DETECT_GOOD = 1;
    //  QE驳回重检验
    public static final Byte IQC_DETECT_REJECT = 2;
    //  未检验
    public static final Byte IQC_DETECT_WAIT = 3;

    /**
     * QE检验 状态
     */
    //  允许良品
    public static final Byte QE_DETECT_GOOD = 1;
    //  未检验
    public static final Byte QE_DETECT_WAIT = 3;

    /**
     * QE确认 状态
     **/
    //  特采
    public static final Byte QE_CONFIRM_FRANCHISE = 1;
    //  退供应商
    public static final Byte QE_CONFIRM_RETURN = 2;
    //  不良，待确认
    public static final Byte QE_CONFIRM_WAIT = 3;

    /**
     * 打印标签 状态
     */
    public static final Byte LABEL_STORAGE_FINISH = 1;
    public static final Byte LABEL_STORAGE_BEING = 2;
    public static final Byte LABEL_STORAGE_PENDING = 3;

    /**
     * 操作记录
     */
    public static final String RECORD_TYPE_ADD = "新增";
    public static final String RECORD_TYPE_MODIFY = "修改";
    public static final String RECORD_TYPE_FINISH = "完成录入";
    public static final String RECORD_TYPE_IQC_DETECT = "IQC检验";
    public static final String RECORD_TYPE_QE_REJECT = "QE驳回";
    public static final String RECORD_TYPE_IQC_RECHECK = "IQC重检";
    public static final String RECORD_TYPE_STORAGE_INVOICE = "生产入库单";
    public static final String RECORD_TYPE_STORAGE_CONFIRM = "确认入库";
}
