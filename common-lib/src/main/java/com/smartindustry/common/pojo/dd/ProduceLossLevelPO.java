package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_produce_loss_level
 * @author 
 */
@Data
public class ProduceLossLevelPO implements Serializable {
    private Long produceLossLevelId;

    private String produceLossLevelName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}