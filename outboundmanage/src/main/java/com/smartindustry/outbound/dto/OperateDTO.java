package com.smartindustry.outbound.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/7/24 14:23
 * @description: 操作DTO
 * @version: 1.0
 */
@Data
public class OperateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long oid;
    private Long rbid;
    private Long plid;
    private Long phid;
    private Long sid;
    private String pid;
    private String lno;
    private Integer num;
    private Byte status;
}
