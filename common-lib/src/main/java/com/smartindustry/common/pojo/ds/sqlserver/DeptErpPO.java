package com.smartindustry.common.pojo.ds.sqlserver;

import java.io.Serializable;
import lombok.Data;

/**
 * 部门
 * @author 
 */
@Data
public class DeptErpPO implements Serializable {
    private Double deptId;

    private String deptCode;

    private String deptName;

    private String parentCode;

    private static final long serialVersionUID = 1L;
}