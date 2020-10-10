package com.smartindustry.common.pojo.bd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_first_goods_funds
 * @author 
 */
@Data
public class FirstGoodsFundsPO implements Serializable {
    private Long id;

    private Date dates;

    private Float costs;

    private static final long serialVersionUID = 1L;
}