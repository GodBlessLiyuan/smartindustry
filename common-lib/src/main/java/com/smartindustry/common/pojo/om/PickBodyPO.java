package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * om_pick_body
 * @author 
 */
@Data
public class PickBodyPO implements Serializable {
    private Long pickBodyId;

    private Long pickHeadId;

    private String materialNo;

    private Integer demandNum;

    private Integer pickNum;

    private Date createTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}