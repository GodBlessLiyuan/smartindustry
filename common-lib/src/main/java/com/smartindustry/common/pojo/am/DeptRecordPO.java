package com.smartindustry.common.pojo.am;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * am_dept_record
 * @author 
 */
@Data
public class DeptRecordPO implements Serializable {
    private Long deptRecordId;

    private Long deptId;

    private Long userId;

    private Date createTime;

    private String type;

    private static final long serialVersionUID = 1L;

    public DeptRecordPO(Long deptId, Long userId, Date createTime, String type) {
        this.deptId = deptId;
        this.userId = userId;
        this.createTime = createTime;
        this.type = type;
    }

    public DeptRecordPO(){

    }
}