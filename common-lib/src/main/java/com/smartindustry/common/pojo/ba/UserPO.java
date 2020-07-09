package com.smartindustry.common.pojo.ba;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ba_user
 * @author 
 */
@Data
public class UserPO implements Serializable {
    private Long userId;

    private String username;

    private String password;

    private Date createTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}