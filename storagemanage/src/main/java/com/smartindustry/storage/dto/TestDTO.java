package com.smartindustry.storage.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/6/30 14:48
 * @description: 质量检验 DTO
 * @version: 1.0
 */
@Data
public class TestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 收料单ID
     */
    private Long rbid;
    /**
     * 状态：1 允许、2 不良
     */
    private Byte status;
    /**
     * 良品数
     */
    private Integer gnum;
    /**
     * 不良数
     */
    private Integer bnum;
}
