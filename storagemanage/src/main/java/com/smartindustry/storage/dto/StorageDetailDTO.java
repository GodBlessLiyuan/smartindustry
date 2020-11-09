package com.smartindustry.storage.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:34 2020/11/6
 * @version: 1.0.0
 * @description:
 */
@Data
public class StorageDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 入库单id
     */
    private Long shid;
    /**
     * 储位id
     */
    private Long lid;
    /**
     * 物料id
     */
    private Long mid;
}
