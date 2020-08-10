package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_process
 * @author 
 */
@Data
public class ProcessPO implements Serializable {
    private Long processId;

    private String processName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}