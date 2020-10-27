package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * om_outbound_record
 * @author 
 */
@Data
public class OutboundRecordPO implements Serializable {
    /**
     * 操作记录
     */
    private Long recordId;

    /**
     * 出库单表头ID
     */
    private Long outboundHeadId;

    /**
     * 操作人ID
     */
    private Long userId;

    /**
     * 操作名称
     */
    private String operationName;

    /**
     * 操作时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}