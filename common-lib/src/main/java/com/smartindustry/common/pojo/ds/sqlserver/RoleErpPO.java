package com.smartindustry.common.pojo.ds.sqlserver;

import java.io.Serializable;
import lombok.Data;

/**
 * 员工角色
 * @author 
 */
@Data
public class RoleErpPO implements Serializable {
    private Double roldId;

    private String roleCode;

    private String roleName;

    private String parentCode;

    private static final long serialVersionUID = 1L;
}