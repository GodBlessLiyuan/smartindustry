package com.smartindustry.common.pojo.bd;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * t_order_goods_num
 * @author 
 */
@Data
public class OrderGoodsNumPO implements Serializable {
    private Long id;

    private Date dates;

    private BigDecimal orderGoodsNum;

    private BigDecimal goodsNumDay;

    private BigDecimal sameRate;

    private BigDecimal circleRate;

    private static final long serialVersionUID = 1L;
}