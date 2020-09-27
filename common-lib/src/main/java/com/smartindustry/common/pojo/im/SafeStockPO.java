package com.smartindustry.common.pojo.im;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * im_safe_stock
 * @author 
 */
@Data
public class SafeStockPO implements Serializable {
    private Long safeStockId;

    private Long materialInventoryId;

    private BigDecimal lowerLimit;

    /**
     * 1：是
2：否
     */
    private Byte way;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}