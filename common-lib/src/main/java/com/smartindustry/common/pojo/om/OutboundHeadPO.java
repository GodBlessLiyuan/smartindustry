package com.smartindustry.common.pojo.om;

import java.io.Serializable;
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
     * 来源类型 : 1 自己新增-没有生产工单号
2 浇注形成的出库单，没有混料
     */
    private Byte sourceType;

    /**
     * 计划出库时间
     */
    private Date planTime;

    /**
     * 完成出库时间
     */
    private Date outboundTime;

    /**
     * 状态 : 1：已出库
2：出库中
3：待出库
     */
    private Byte status;

    /**
     * 备注
     */
    private String extra;

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