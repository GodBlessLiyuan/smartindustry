package com.smartindustry.authority.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/7/24 14:23
 * @description: 操作DTO
 * @version: 1.0
 */
@Data
public class OperateDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long did;
    private Byte status;
    private Long uid;
    private String password;
    private String name;
    private Long rid;
    private String rname;
}
