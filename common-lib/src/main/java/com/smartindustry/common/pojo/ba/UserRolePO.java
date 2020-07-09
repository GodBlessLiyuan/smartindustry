package com.smartindustry.common.pojo.ba;

import java.io.Serializable;
import lombok.Data;

/**
 * ba_user_role
 * @author 
 */
@Data
public class UserRolePO implements Serializable {
    private Long userRoleId;

    private Long userId;

    private Long roleId;

    private static final long serialVersionUID = 1L;
}