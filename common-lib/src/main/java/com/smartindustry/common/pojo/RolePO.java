package com.smartindustry.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ba_role
 * @author 
 */
@Data
public class RolePO implements Serializable {
    private Long roleId;

    private String code;

    private String name;

    private Date createTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}