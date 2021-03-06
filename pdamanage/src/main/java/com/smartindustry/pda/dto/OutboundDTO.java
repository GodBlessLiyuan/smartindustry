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
public class OutboundDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**出库单表头ID
     *
     */
    private Long ohid;
}
