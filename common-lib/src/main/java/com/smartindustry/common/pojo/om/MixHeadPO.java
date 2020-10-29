package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * om_mix_head
 * @author 
 */
@Data
public class MixHeadPO implements Serializable {
    /**
     * 混料单表头id
     */
    private Long mixHeadId;

    /**
     * 混料单编码
     */
    private String mixNo;

    /**
     * 计划出库时间
     */
    private Date planTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 : 1 未删除
2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}