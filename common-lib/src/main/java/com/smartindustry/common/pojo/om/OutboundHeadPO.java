package com.smartindustry.common.pojo.om;

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
    private Long outboundHeadId;

    private String outboundNo;

    private String sourceNo;

    /**
     *  
     */
    private Byte sourceType;

    private Date planTime;

    private Date outboundTime;

    private BigDecimal expectNum;

    private BigDecimal outboundNum;

    private String extra;

    /**
     * 1：已出库
2：出库中
3：待出库
     */
    private Byte status;

    private Date createTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}