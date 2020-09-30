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
    //  退供应商
    public static final Byte RECEIPT_RETURN_SUPPLIER = 30;

    /**
     * IQC检验 状态
     */
    //  允许良品
    public static final Byte IQC_ALLOW = 1;
    //  QE驳回重检验
    public static final Byte IQC_REJECT = 2;
    //  未检验
    public static final Byte IQC_WAIT = 3;

    /**
     * QE确认|检验 状态
     */
    //  允许
    public static final Byte QE_ALLOW = 1;
    // 驳回
    public static final Byte QE_REJECT = 2;
    //  未检验
    public static final Byte QE_WAIT = 3;
    //  特采
    public static final Byte QE_FRANCHISE = 4;
    //  退供应商
    public static final Byte QE_RETURN = 5;

    /**
     * 物料入库
     */
    //  已入库
    public static final Byte MATERIAL_STORAGE_FINISH = 1;
    //  入库中
    public static final Byte MATERIAL_STORAGE_BEING = 2;
    //  待入库
    public static final Byte MATERIAL_STORAGE_PENDING = 3;

    // 良品
    public static final Byte STORAGE_TYPE_GOOD = 1;

    /**
     * 打印标签 类型
     */
    public static final Byte LABEL_ORIGIN_SCAN = 1;
    public static final Byte LABEL_ORIGIN_ENTRY = 2;
    public static final Byte LABEL_TYPE_GOOD = 1;
    public static final Byte LABEL_TYPE_BAD = 2;

    /**
     * 物料类型： 良品 非良品
     */
    public static final Byte MATERIAL_TYPE_GOOD = 1;
    public static final Byte MATERIAL_TYPE_BAD = 2;
    /**
     * IQC 检验
     */
    public static final Byte STORAGE_INSPECT_IQC = 1;
    /**
     * QE 检验
     */
    public static final Byte STORAGE_INSPECT_QE = 2;
    /**
     * 不启动
     */
    public static final Byte STORAGE_INSPECT_NO = 3;

    /**
     * 入库标签 状态  调拨入库
     */
    public static final Byte STORAGE_LABEL_TRANSFER = 11;


    public static final Byte STORAGE_SOURCE_TYPE_RECEIPT = 1;

    public static final Byte STORAGE_SOURCE_TYPE_TRANSFER = 2;


    /**
     * 操作记录
     */
    public static final String RECORD_TYPE_ADD = "新增";
    public static final String RECORD_TYPE_FINISH = "完成录入";
    public static final String RECORD_TYPE_IQC_DETECT_ALLOW = "IQC检验-允许";
    public static final String RECORD_TYPE_IQC_DETECT_BAD = "IQC检验-不良";
    public static final String RECORD_TYPE_IQC_QE_REJECT = "QE驳回";
    public static final String RECORD_TYPE_IQC_RECHECK_ALLOW = "IQC重检-允许";
    public static final String RECORD_TYPE_IQC_RECHECK_BAD = "IQC重检-不良";
    public static final String RECORD_TYPE_QE_DETECT_ALLOW = "QE检验-允许";
    public static final String RECORD_TYPE_QE_DETECT_FRANCHISE = "QE检验-特采";
    public static final String RECORD_TYPE_QE_DETECT_RETURN = "QE检验-退供应商";
    public static final String RECORD_TYPE_QE_CONFIRM_ADD = "形成QE确认单";
    public static final String RECORD_TYPE_QE_CONFIRM_FRANCHISE = "QE确认-特采";
    public static final String RECORD_TYPE_QE_CONFIRM_REJECT = "QE确认-驳回";
    public static final String RECORD_TYPE_QE_CONFIRM_RETURN = "QE确认-退供应商";
    public static final String RECORD_TYPE_STORAGE_INVOICE = "形成入库单";
    public static final String RECORD_TYPE_STORAGE_CONFIRM = "确认入库";
}
