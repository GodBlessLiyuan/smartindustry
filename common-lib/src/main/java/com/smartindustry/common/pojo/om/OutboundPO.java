package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * om_outbound
 * @author 
 */
@Data
public class OutboundPO implements Serializable {
    private Long outboundId;

    private Long pickHeadId;

    private String outboundNo;

    private Date outboundTime;

    private Date shipTime;

    /**
     * 1：已出库
2：待出库
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