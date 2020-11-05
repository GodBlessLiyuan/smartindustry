package com.smartindustry.pda.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:17 2020/11/4
 * @version: 1.0.0
 * @description:
 */
@Data
public class SpareMaterialVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String rfid;
    /**
     * 物料名称
     */
    private String mname;
    /**
     * 物料规格
     */
    private String mmodel;
    /**
     * 数量
     */
    private BigDecimal num;


    private Boolean flag;
}
