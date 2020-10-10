package com.smartindustry.common.pojo.bd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_goods_rotation_rate
 * @author 
 */
@Data
public class GoodsRotationRatePO implements Serializable {
    private Long id;

    private Date dates;

    private Integer rotationNum;

    private Float sameRate;

    private Float circleRate;

    private static final long serialVersionUID = 1L;
}