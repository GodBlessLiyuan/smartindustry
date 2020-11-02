package com.smartindustry.storage.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:42 2020/11/2
 * @version: 1.0.0
 * @description: 有关备料区的dto
 */
@Data
public class StoragePreDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 叉车id
     */
    private Long fid;
    /**
     * 成品RFID
     */
    private String prfid;
    /**
     * 储位RFID
     */
    private String lrfid;
    /**
     * 操作行为：1 直接成品入库 2 入备料区 3 备料区入成品区
     */
    private Byte type;
}
