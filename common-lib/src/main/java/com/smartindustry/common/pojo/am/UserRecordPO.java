package com.smartindustry.common.pojo.am;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * am_user_record
 * @author 
 */
@Data
public class UserRecordPO implements Serializable {
    private Long userRecordId;

    private Long userId;

    private Long operateId;

    private Date createTime;

    private String type;

    private static final long serialVersionUID = 1L;

    public UserRecordPO(Long userId, Long operateId, Date createTime, String type) {
        this.userId = userId;
        this.operateId = operateId;
        this.createTime = createTime;
        this.type = type;
    }

    public UserRecordPO(){}
}