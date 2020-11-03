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
    private Long outboundBodyId;

    private Long outboundHeadId;

    private Long materialId;

    private BigDecimal expectNum;

    private BigDecimal outboundNum;

    private Date createTime;

    private Date outboundTime;

    /**
     * 1 未删除
2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}