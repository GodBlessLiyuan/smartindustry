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
    private Long bomRecordId;

    private Long bomHeadId;

    private Long userId;

    private Date createTime;

    private String type;

    public BomRecordPO(Long bomHeadId, Long userId, Date createTime, String type) {
        this.bomHeadId = bomHeadId;
        this.userId = userId;
        this.createTime = createTime;
        this.type = type;
    }
    public BomRecordPO(){}

    private static final long serialVersionUID = 1L;
}