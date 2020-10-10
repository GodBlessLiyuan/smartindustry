package com.smartindustry.common.pojo.bd;

import java.io.Serializable;
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

    private Float goodsFunds;

    private Float sameRate;

    private Float circleRate;

    private static final long serialVersionUID = 1L;
}