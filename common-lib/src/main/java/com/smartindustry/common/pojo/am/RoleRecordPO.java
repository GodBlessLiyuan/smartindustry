package com.smartindustry.common.pojo.am;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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

    public RoleRecordPO(Long roleId, Long userId, Date createTime, String type) {
        this.roleId = roleId;
        this.userId = userId;
        this.createTime = createTime;
        this.type = type;
    }

    public RoleRecordPO(){}
}