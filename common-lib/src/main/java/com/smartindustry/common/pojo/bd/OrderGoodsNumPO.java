package com.smartindustry.common.pojo.bd;

import java.io.Serializable;
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

    private Long orderGoodsNum;

    private Integer goodsNumDay;

    private Float sameRate;

    private Float circleRate;

    private static final long serialVersionUID = 1L;
}