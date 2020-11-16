package com.smartindustry.pda.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/11/4 20:44
 * @description: 公共 DTO
 * @version: 1.0
 */
@Data
public class CommonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * IMEI 号
     */
    private String imei;
    /**
     * 类别类型：1-待执行，2-执行中，3-已执行
     * 数采类型：1-叉起，2-放下
     */
    private Byte type;
    /**
     * 物料砧板RFID
     */
    private String mrfid;
    /**
     * 库位RFID
     */
    private String lrfid;
}
