package com.smartindustry.common.pojo.wo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * wo_slurry_order
 * @author 
 */
@Data
public class SlurryOrderPO implements Serializable {
    /**
     * 料浆工单ID
     */
    private Long slurryId;

    /**
     * 工单编号
     */
    private String slurryNo;

    /**
     * 计划制作日期
     */
    private Date planDate;

    /**
     * 状态 : 1. 待下发
2. 下发中
3. 待完成
4. 已完成
     */
    private Byte status;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 完成人
     */
    private Long finisher;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 : 1 未删除
2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}