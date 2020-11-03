package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import lombok.Data;

/**
 * om_outbound_forklift
 * @author 
 */
@Data
public class OutboundForkliftPO implements Serializable {
    /**
     * 出库叉车id
     */
    private Long outboundForkliftId;

    /**
     * 出库单表头ID
     */
    private Long outboundHeadId;

    /**
     * 叉车id
     */
    private Long forkliftId;

    /**
     * RFID
     */
    private String rfid;

    private static final long serialVersionUID = 1L;
}