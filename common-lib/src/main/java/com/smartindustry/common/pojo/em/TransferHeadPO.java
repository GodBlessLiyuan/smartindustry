package com.smartindustry.common.pojo.em;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * em_transfer_head
 * @author 
 */
@Data
public class TransferHeadPO implements Serializable {
    private Long transferHeadId;

    private String transferNo;

    /**
     * 1：工单调拨
2：不良调拨
     */
    private Byte transferType;

    private Long outboundWid;

    private Long storageWid;

    private Date planTime;

    /**
     * 1：已入库
2：入库中
3：待入库
     */
    private Byte outboundStatus;

    /**
     * 1：已入库
2：入库中
3：待入库
     */
    private Byte storageStatus;

    /**
     * 1：已审核
2：驳回
3：待审核
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