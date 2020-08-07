package com.smartindustry.authority.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:18 2020/8/7
 * @version: 1.0.0
 * @description:
 */
@Data
public class EditDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long uid;
    /**
     * 新密码
     */
    private String npassword;
    /**
     * 旧密码
     */
    private String opassword;
}
