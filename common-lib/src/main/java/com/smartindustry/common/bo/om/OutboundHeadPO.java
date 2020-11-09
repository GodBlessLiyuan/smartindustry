package com.smartindustry.common.bo.om;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * om_outbound_head
 * @author 
 */
@Data
public class OutboundHeadPO implements Serializable {
    /**
     * 出库单表头ID
     */
    private Long outboundHeadId;

    /**
     * 出库单编码
     */
    private String outboundNo;

    /**
     * 来源单号
     */
    private String sourceNo;

    /**
     * 来源类型 : 1 原材料出库
2 销售出库
3 备料区出库
     */
    private Byte sourceType;

    /**
     * 客户编码
     */
    private String clientNo;

    /**
     * 计划出库时间
     */
    private Date planTime;

    /**
     * 完成出库时间
     */
    private Date outboundTime;

    /**
     * 期望出库数
     */
    private BigDecimal expectNum;

    /**
     * 已经出库数量
     */
    private BigDecimal outboundNum;

    /**
     * 备注
     */
    private String extra;

    /**
     * 状态 : 1：已出库
2：出库中
3：待出库
     */
    private Byte status;

    /**
     * 接受地址
     */
    private String acceptAddress;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 : 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}