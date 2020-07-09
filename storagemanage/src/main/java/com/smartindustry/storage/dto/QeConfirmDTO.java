package com.smartindustry.storage.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/7/1 11:36
 * @description: QE确认
 * @version: 1.0
 */
@Data
public class QeConfirmDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 收料单ID
     */
    private Long rbid;
    /**
     * 2:驳回，4:特采，5:退供应商
     */
    private Byte status;
    /**
     * 特采数量
     */
    private Integer num;
    /**
     * 备注
     */
    private String remark;
}
