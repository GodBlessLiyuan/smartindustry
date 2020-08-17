package com.smartindustry.basic.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:33 2020/8/17
 * @version: 1.0.0
 * @description:
 */
@Data
public class FlowLayoutDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 销售，采购，生产PID强关联
     */
    private Boolean rpid;
}
