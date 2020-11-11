package com.smartindustry.common.pojo.am;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * am_role_record
 * @author 
 */
@Data
public class RoleRecordPO implements Serializable {
    private Long roleRecordId;

    private Long roleId;

    private Long userId;

    private Date createTime;

    private String type;

    private static final long serialVersionUID = 1L;

    public RoleRecordPO(Long roleId, Long userId, String type) {
        this.roleId = roleId;
        this.userId = userId;
        this.createTime = new Date();
        this.type = type;
    }

    public RoleRecordPO(){}

}