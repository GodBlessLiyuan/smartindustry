package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.om.PickHeadPO;
import lombok.Data;

import java.util.Date;


/**
 * @author: jiangzhaojie
 * @date: Created in 9:40 2020/7/17
 * @version: 1.0.0
 * @description:
 */
@Data
public class PickHeadBO extends PickHeadPO {
    private Long materialId;
    /**
     * 物料编号
     */
    private String materialNo;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 物料描述
     */
    private String materialDesc;
    /**
     * 当前物料使用了推荐的pid,以逗号隔开
     */
    private String recommendPid;
    /**
     * 当前物料使用了未推荐的pid,以逗号隔开
     */
    private String noRecommendPid;

    /**
     * 异常说明情况
     */
    private String aberrantDesc;

    /**
     * 调拨订单id
     */
    private Long transferId;
    /**
     * 调拨订单编号
     */
    private String transferNo;
    /**
     * 调拨订单类型
     */
    private Byte transferType;

    /**
     * 计划调拨时间
     */
    private Date thPlanTime;

    /**
     * 调拨出库状态
     */
    private Byte thOutboundStatus;
}
