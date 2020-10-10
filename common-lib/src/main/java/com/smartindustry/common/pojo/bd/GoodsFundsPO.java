package com.smartindustry.common.pojo.bd;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * t_goods_funds
 * @author 
 */
@Data
public class GoodsFundsPO implements Serializable {
    private Long id;

    private Date dates;

    private BigDecimal goodsFunds;

    private BigDecimal sameRate;

    private BigDecimal circleRate;

    private static final long serialVersionUID = 1L;
}