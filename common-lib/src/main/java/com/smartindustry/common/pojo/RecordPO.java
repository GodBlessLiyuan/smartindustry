package com.smartindustry.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sm_record
 * @author 
 */
@Data
public class RecordPO implements Serializable {
    private Long recordId;

    private Long userId;

    private String name;

    private Byte type;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}