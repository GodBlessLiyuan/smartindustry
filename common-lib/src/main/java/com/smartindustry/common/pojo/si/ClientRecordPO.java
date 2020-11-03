package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_client_record
 * @author 
 */
@Data
public class ClientRecordPO implements Serializable {
    /**
     * 操作记录ID
     */
    private Long clientRecordId;

    /**
     * 客户id
     */
    private Long clientId;

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