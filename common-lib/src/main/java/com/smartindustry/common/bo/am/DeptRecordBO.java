package com.smartindustry.common.bo.am;

import com.smartindustry.common.pojo.am.DeptRecordPO;
import lombok.*;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 13:14 2020/8/6
 * @version: 1.0.0
 * @description:
 */
@Data
public class DeptRecordBO extends DeptRecordPO {
    private static final long SerialVersionUID = 1L;

    private String deptName;

    private String name;

    public DeptRecordBO(Long deptId, Long userId, Date createTime, String type, String deptName, String name) {
        super(deptId, userId, createTime, type);
        this.deptName = deptName;
        this.name = name;
    }

    public DeptRecordBO(){

    }
}
