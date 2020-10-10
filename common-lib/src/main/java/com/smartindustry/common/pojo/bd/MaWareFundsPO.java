package com.smartindustry.common.pojo.bd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_ma_ware_funds
 * @author 
 */
@Data
public class MaWareFundsPO implements Serializable {
    private Long id;

    private Date dates;

    private Float maWareFunds;

    private Float sameRate;

    private Float circleRate;

    private static final long serialVersionUID = 1L;
}