package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_forklift_record
 * @author 
 */
@Data
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
     * 操作名称
     */
    private String operationName;

    /**
     * 操作时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}