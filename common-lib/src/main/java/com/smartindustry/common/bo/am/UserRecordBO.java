package com.smartindustry.common.bo.am;

import com.smartindustry.common.pojo.am.UserRecordPO;
import lombok.Data;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 13:18 2020/8/6
 * @version: 1.0.0
 * @description:
 */
@Data
public class UserRecordBO extends UserRecordPO {
    private static final long SerialVersionUID = 1L;
    private String userName;

    private String operateName;

    public UserRecordBO(Long userId, Long operateId, Date createTime, String type, String userName, String operateName) {
        super(userId, operateId, createTime, type);
        this.userName = userName;
        this.operateName = operateName;
    }

    public UserRecordBO(){}
}
