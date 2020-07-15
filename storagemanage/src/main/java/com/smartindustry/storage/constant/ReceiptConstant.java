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
    public static final Byte MATERIAL_STORAGE_GOOD = 1;

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
     * 原材料
     */
    public static final Byte MATERIAL_TYPE_RAW = 1;
    /**
     * 半成品
     */
    public static final Byte MATERIAL_TYPE_SEMI = 2;
    /**
     * 成品
     */
    public static final Byte MATERIAL_TYPE_FINISH = 3;

    /**
     * 操作记录
     */
    public static final String RECORD_TYPE_ADD = "新增";
    public static final String RECORD_TYPE_FINISH = "完成录入";
    public static final String RECORD_TYPE_IQC_DETECT = "IQC检验";
    public static final String RECORD_TYPE_QE_REJECT = "QE驳回";
    public static final String RECORD_TYPE_IQC_RECHECK = "IQC重检";
    public static final String RECORD_TYPE_QE_DETECT = "QE检验";
    public static final String RECORD_TYPE_QE_FRANCHISE = "QE特采";
    public static final String RECORD_TYPE_QE_RETURN = "QE退供应商";
    public static final String RECORD_TYPE_STORAGE_INVOICE = "生成入库单";
    public static final String RECORD_TYPE_STORAGE_CONFIRM = "确认入库";
}
