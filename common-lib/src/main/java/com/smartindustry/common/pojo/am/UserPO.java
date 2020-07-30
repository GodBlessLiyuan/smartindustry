package com.smartindustry.common.pojo.am;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * am_user
 * @author 
 */
@Data
public class UserPO implements Serializable {
    private Long userId;

    private String name;

    /**
     * 1 男
2 女
     */
    private Byte sex;

    private Long deptId;

    private String username;

    private String password;

    private Long roleId;

    private String job;

    private String phone;

    private String email;

    /**
     * 1 启用
2 禁用
     */
    private Byte status;

    private String remark;

    private Date createTime;

    private Date updateTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}