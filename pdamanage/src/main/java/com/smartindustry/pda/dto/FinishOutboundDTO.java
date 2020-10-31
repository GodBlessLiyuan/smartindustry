package com.smartindustry.pda.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/10/30 10:06
 * @description: pda 操作
 * @version: 1.0
 */
@Data
public class FinishOutboundDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * IMEI 号
     */
    private String imei;
    /**
     * 类别类型：1-待执行，2-执行中，3-已执行
     */
    private Byte type;
    /**
     * 出库单表头ID
     */
    private Long ohid;
}
