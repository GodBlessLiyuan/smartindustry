package com.smartindustry.storage.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/7/6 14:30
 * @description: QE检验
 * @version: 1.0
 */
@Data
public class QeTestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 收料单ID
     */
    private Long rbid;
    /**
     * 1:允许，2:特采，3:退供应商
     */
    private Byte status;
    /**
     * 特采数量/良品数量
     */
    private Integer gnum;
    /**
     * 不良数量
     */
    private Integer bnum;
    /**
     * 备注
     */
    private String remark;
}
