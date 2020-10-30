package com.smartindustry.pda;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/10/30 10:06
 * @description: TODO
 * @version: 1.0
 */
@Data
public class FinishOutboundDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String imei;
}
