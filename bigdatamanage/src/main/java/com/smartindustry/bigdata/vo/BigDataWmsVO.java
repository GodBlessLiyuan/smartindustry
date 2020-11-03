package com.smartindustry.bigdata.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: xiahui
 * @date: Created in 2020/9/28 8:18
 * @description: TODO
 * @version: 1.0
 */
@Data
public class BigDataWmsVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 当月订单发货数
     */
    private A osnum;
    /**
     * 当月订单按时发货率
     */
    private B odrate;
    /**
     * 当月订单发货金额
     */
    private A osmoney;
    /**
     * 当月原材料采购入库金额（RMB）
     */
    private A psmoney;
    /**
     * 当月原材料库存金额(RMB)
     */
    private A rimoney;
    /**
     * 当月存货库存金额(RMB)
     */
    private A iimoney;
    /**
     * 当月存货周转率
     */
    private A iturnover;

    @Data
    public static class A {
        /**
         * 数量/金额
         */
        private BigDecimal total;
        /**
         * 月同比
         */
        private BigDecimal moy;
        /**
         * 月环比
         */
        private BigDecimal mom;
        /**
         * 日均
         */
        private BigDecimal average;
    }

    @Data
    public static class B {
        /**
         * 发货率
         */
        private BigDecimal rate;
        /**
         * 发货数
         */
        private Integer num;
    }
}
