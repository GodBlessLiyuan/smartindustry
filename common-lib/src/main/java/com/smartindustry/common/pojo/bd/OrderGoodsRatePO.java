package com.smartindustry.common.pojo.bd;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * t_order_goods_rate
 * @author 
 */
@Data
public class OrderGoodsRatePO implements Serializable {
    private Long id;

    private Date dates;

    private BigDecimal orderGoodsRate;

    private Integer goodsNum;

    private static final long serialVersionUID = 1L;
}