package com.smartindustry.common.pojo.am;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * am_role
 * @author 
 */
@Data
public class RolePO implements Serializable {
    private Long roleId;

    private String roleName;

    private String roleDesc;

    /**
     * 1：启用
2：禁用
     */
    private Byte status;

    private Date createTime;

    private Date updateTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    public boolean isAdmin() {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId) {
        return roleId != null && 1L == roleId;
    }

    private static final long serialVersionUID = 1L;
}