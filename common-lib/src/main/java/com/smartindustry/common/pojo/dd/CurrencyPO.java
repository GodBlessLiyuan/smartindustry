package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_currency
 * @author 
 */
@Data
public class CurrencyPO implements Serializable {
    private Long currencyId;

    private String currencyName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}