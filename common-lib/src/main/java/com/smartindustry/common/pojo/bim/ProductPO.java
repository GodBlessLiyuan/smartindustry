package com.smartindustry.common.pojo.bim;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * bim_product
 * @author 
 */
@Data
public class ProductPO implements Serializable {
    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 产品编号
     */
    private String productNo;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 型号级别
     */
    private String productModel;

    /**
     * 产品规格
     */
    private String productStandard;

    /**
     * 产品描述
     */
    private String productDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}