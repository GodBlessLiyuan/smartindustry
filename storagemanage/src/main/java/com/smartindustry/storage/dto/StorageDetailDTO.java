package com.smartindustry.storage.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/7/6 16:48
 * @description: 物料入库详情
 * @version: 1.0
 */
@Data
public class StorageDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 入库单Id
     */
    private Long sid;
    /**
     * 入库分组Id
     */
    private Long sgid;
    /**
     * 入库详情Id
     */
    private Long sdid;
    /**
     * 打印标签Id
     */
    private Long plid;
}
