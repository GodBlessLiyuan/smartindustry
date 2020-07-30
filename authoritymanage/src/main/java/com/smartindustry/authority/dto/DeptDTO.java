package com.smartindustry.authority.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:34 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@Data
public class DeptDTO {
    private Long did;
    private String dname;

    /**
     * 1 启动
     2 禁用
     */
    private Byte status;
}
