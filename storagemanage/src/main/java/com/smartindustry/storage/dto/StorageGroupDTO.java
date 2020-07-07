package com.smartindustry.storage.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/7/6 16:10
 * @description: 物料入库组
 * @version: 1.0
 */
@Data
public class StorageGroupDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 入库单ID
     */
    private Long sid;
    /**
     * 收料单ID
     */
    private Long rbid;
    /**
     * 物料入库组ID
     */
    private Long sgid;
    /**
     * 库位编号
     */
    private String lno;
    /**
     * 打印标签PID
     */
    private String pid;
}
