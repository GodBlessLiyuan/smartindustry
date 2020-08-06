package com.smartindustry.common.pojo.am;

import java.io.Serializable;
import lombok.Data;

/**
 * am_authority
 * @author 
 */
@Data
public class AuthorityPO implements Serializable {
    private Long authorityId;

    private String authorityName;

    /**
     * 1：菜单   2：按钮
     */
    private Byte type;

    private Long parentId;

    private String authorityPath;

    private static final long serialVersionUID = 1L;
}