package com.smartindustry.storage.dto;

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

    /**
     * 收料单ID
     */
    private Long rbid;
    /**
     * 标签ID
     */
    private Long plid;
    /**
     * 入库单ID
     */
    private Long sid;
    /**
     *
     */
    private String pid;
    /**
     * 库位编号
     */
    private String lno;
    private Integer num;
    private Byte status;
    /**
        入库详情组ID
     */
    private Long sgid;

    /**
     * 库房ID
     */
    private Long whid;

}
