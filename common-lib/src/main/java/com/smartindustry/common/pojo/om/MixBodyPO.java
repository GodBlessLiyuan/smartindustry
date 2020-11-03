package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * om_mix_body
 * @author 
 */
@Data
public class MixBodyPO implements Serializable {
    /**
     * 混料单表体id
     */
    private Long mixBodyId;

    /**
     * 混料单表头id
     */
    private Long mixHeadId;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 计划出库数量
     */
    private BigDecimal planNum;

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