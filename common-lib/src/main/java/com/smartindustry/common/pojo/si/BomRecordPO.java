package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_bom_record
 * @author 
 */
@Data
public class BomRecordPO implements Serializable {
    /**
     * 操作记录ID
     */
    private Long bomRecordId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 操作名称
     */
    private String operationName;

    private static final long serialVersionUID = 1L;
}