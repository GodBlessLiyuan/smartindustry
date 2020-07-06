package com.smartindustry.storage.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/7/6 10:04
 * @description: 标签拆分
 * @version: 1.0
 */
@Data
public class LabelSplitDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已有PID
     */
    private String pid;
    /**
     * 良品包装数量
     */
    private Integer gnum;
    /**
     * 不良品包装数量
     */
    private Integer bnum;
}
