package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.om.OutboundPO;
import lombok.Data;

import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/16 15:50
 * @description: 出库单 BO
 * @version: 1.0
 */
@Data
public class OutboundBO extends OutboundPO {
    /**
     * 拣货单号
     */
    private String pickNo;
    /**
     * 来源单号
     */
    private String orderNo;
    /**
     * 来源类型
     * 1：工单
     * 2：销售订单
     */
    private Byte orderType;

    /**
     * 对应项目
     */
    private String correspondProject;
    /**
     * 接受客户
     */
    private String acceptCustomer;
    /**
     * 接受地址
     */
    private String acceptAddress;
    /**
     * 计划发货时间
     */
    private Date planTime;
}
