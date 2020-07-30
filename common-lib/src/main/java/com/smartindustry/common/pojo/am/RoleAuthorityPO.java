package com.smartindustry.common.pojo.am;

import java.io.Serializable;
import lombok.Data;

/**
 * am_role_authority
 * @author 
 */
@Data
public class RoleAuthorityPO implements Serializable {
    private Long roleAuthorityId;

    private Long roleId;

    private Long authorityId;

    private static final long serialVersionUID = 1L;
}