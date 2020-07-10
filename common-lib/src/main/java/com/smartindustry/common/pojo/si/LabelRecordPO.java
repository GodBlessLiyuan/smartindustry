package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sm_label_record
 * @author 
 */
@Data
public class LabelRecordPO implements Serializable {
    private Long labelRecordId;

    private Long printLabelId;

    private Long userId;

    private String name;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}