package com.smartindustry.common.pojo.pda;

import java.io.Serializable;
import lombok.Data;

/**
 * pda_outbound_forklift
 * @author 
 */
@Data
public class OutboundForkliftPO implements Serializable {
    private Long outboundForkliftId;

    private Long outboundHeadId;

    private Long forkliftId;

    private String rfid;

    private static final long serialVersionUID = 1L;
}