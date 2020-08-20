package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * om_pick_head
 * @author 
 */
@Data
public class PickHeadPO implements Serializable {
    private Long pickHeadId;

    private String pickNo;

    private String orderNo;

    /**
     * 1：工单
2：销售订单
     */
    private Byte orderType;

    /**
     * 1：待推荐
5：未处理
10：物料拣货
15：工单审核|OQC检验
20：等齐套发货
25：取消发货，退货仓库
30：物料出库
35：完成出库
40：确认出库
     */
    private Byte materialStatus;

    private String correspondProject;

    private String acceptCustomer;

    private String acceptAddress;

    private Date planTime;

    private Date outboundTime;

    /**
     * 1：全部出库
2：欠料出库
3：未出库
     */
    private Byte outboundStatus;

    private Date createTime;

    private Date updateTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}