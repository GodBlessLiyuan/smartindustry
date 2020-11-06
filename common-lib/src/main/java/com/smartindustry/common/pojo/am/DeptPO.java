package com.smartindustry.common.pojo.am;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * am_dept
 * @author 
 */
@Data
public class DeptPO implements Serializable {
    private Long deptId;

    private Long parentId;

    private String deptCode;

    private String deptName;

    private Long userId;

    private String deptDesc;

    /**
     * 1 启动
2 禁用
     */
    private Byte status;

    private Date createTime;

    private Date updateTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}