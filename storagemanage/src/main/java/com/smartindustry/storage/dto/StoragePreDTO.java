package com.smartindustry.storage.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

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
    private String imei;
    /**
     * 成品RFID
     */
    private String prfid;
    /**
     * 储位RFID
     */
    private String lrfid;



    /**
     * 当前栈板入库数 1栈板为一单位
     */
    private BigDecimal num;
    /**
     * 操作行为：1 直接成品入库 2 入备料区 3 备料区入成品区
     */
    private Byte type;
}
