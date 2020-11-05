package com.smartindustry.common.pojo.ds.sqlserver;

import java.io.Serializable;
import lombok.Data;

/**
 * 操作员
 * @author 
 */
@Data
public class OperatorPO implements Serializable {
    private Double operatorId;

    private String operatorNo;

    private String operatorName;

    private String password;

    private Double roleId;

    private Double deptId;

    private static final long serialVersionUID = 1L;
}