package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * om_outbound_body
 * @author 
 */
@Data
public class OutboundBodyPO implements Serializable {
    /**
     * 出库单表体id
     */
    private Long outboundBodyId;

    /**
     * 出库单表头ID
     */
    private Long outboundHeadId;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 计划出库数量
     */
    private Integer planNum;

    /**
     * 出库数量
     */
    private Integer demandNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 出库时间
     */
    private Date outboundTime;

    /**
     * 是否删除 : 1 未删除
2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}