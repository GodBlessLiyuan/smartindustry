package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * om_outbound_body
 * @author 
 */
@Data
public class OutboundBodyPO implements Serializable {
    /**
     * 出库单表体id
     */
    private Long outboundBodyId;

    /**
     * 出库单表头ID
     */
    private Long outboundHeadId;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 期望出库数量
     */
    private BigDecimal expectNum;

    /**
     * 已出库数量
     */
    private BigDecimal outboundNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 出库时间
     */
    private Date outboundTime;

    /**
     * 是否删除 : 1 未删除
2 已删除
     */
    private Byte dr;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 金额
     */
    private BigDecimal totalPrice;

    /**
     * 备注
     */
    private String extra;

    private static final long serialVersionUID = 1L;
}