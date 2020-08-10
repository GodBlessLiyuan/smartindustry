package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_bom_head
 * @author 
 */
@Data
public class BomHeadPO implements Serializable {
    private Long bomHeadId;

    private Long materialId;

    private Integer relateNum;

    private Long userId;

    private Date createTime;

    private Date updateTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}