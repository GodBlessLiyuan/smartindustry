package com.smartindustry.common.pojo.si;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * si_forklift_record
 * @author 
 */
@Data
@NoArgsConstructor
public class ForkliftRecordPO implements Serializable {
    /**
     * 操作记录id
     */
    private Long recordId;

    /**
     * 叉车id
     */
    private Long forkliftId;

    /**
     * 操作人ID
     */
    private Long userId;

    /**
     * 操作名称
     */
    private String operationName;

    /**
     * 操作时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public ForkliftRecordPO(Long forkliftId, Long userId, String operationName) {
        this.forkliftId = forkliftId;
        this.userId = userId;
        this.operationName = operationName;
        this.createTime = new Date();
    }
}