package com.smartindustry.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * ba_role_authority
 * @author 
 */
@Data
public class RoleAuthorityPO implements Serializable {
    private Long roleAuthorityId;

    private Long roleId;

    private Long authorityId;

    private static final long serialVersionUID = 1L;
}