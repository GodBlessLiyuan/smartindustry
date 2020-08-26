package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.om.PickHeadPO;
import lombok.Data;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:28 2020/8/26
 * @version: 1.0.0
 * @description:
 */
@Data
public class PickInfoBO extends PickHeadPO {

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
