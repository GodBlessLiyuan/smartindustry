package com.smartindustry.authority.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:16 2020/8/3
 * @version: 1.0.0
 * @description:
 */
@Data
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String code;
//    private String uuid;
}
