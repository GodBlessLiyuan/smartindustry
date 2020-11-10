package com.smartindustry.datasynchronize.constant;

/**
 * @author hui.feng
 * @date created in 2020/11/5
 * @description
 */
public class SyncConstant {

    /**
     * 材料类型
     * 117 ERP 原材料类型
     * 120 ERP 产品类型
     *
     * 2. WMS 产品类型
     * 1. WMS 原材料类型
     */

    public static final Double TYPE_ERP_MATERIAL_RAW=117.0;

    public static final Double TYPE_ERP_MATERIAL_PRODUCT=120.0;

    public static final Byte TYPE_MATERIAL_PRODUCT = 2;

    public static final Byte TYPE_MATERIAL_RAW = 1;

    /**
     * 9   ERP 现金支付
     * 10 ERP 欠款
     *
     * 1 WMS 现金支付
     * 2. WMS 欠款
     */

    public static final Double PAY_METHOD_ERP_BILL= 9.0;

    public static final Double PAY_METHOD_ERP_DEBT= 10.0;

    public static final String PAY_NAME_ERP_BILL= "现金";

    public static final String PAY_NAME_ERP_DEBT= "现金";

    public static final Byte PAY_METHOD_BILL= 1;

    public static final Byte PAY_METHOD_DEBT= 2;

}
