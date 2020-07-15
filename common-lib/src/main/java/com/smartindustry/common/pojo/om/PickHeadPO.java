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
     * 1：未处理
5：物料拣货
10：工单审核|OQC检验
15：等齐套发货
20：取消发货，退货仓库
25：物料出库
30：完成出库
35：确认出库
     */
    private Byte materialStatus;

    private String correspondProject;

    private Date planTime;

    private Date outboundTime;

    /**
     * 1：全部出库
2：欠料出库
3：未出库
     */
    private Byte outboundStatus;

    private Date createTime;

    /**
     * 1：未删除
2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}