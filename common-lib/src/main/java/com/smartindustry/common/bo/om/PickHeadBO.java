package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.om.PickHeadPO;
import lombok.Data;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 20:13 2020/7/15
 * @version: 1.0.0
 * @description:
 */
@Data
public class PickHeadBO extends PickHeadPO {
    private static final long SerialVersionUID = 1L;
    /**
     * 拣货单号
     */
    private String pickNO;
    /**
     * 物料状态
     */
    private Byte materialStatus;
    /**
     * 工单号
     */
    private String orderNo;
    /**
     * 对应项目
     */
    private String correspondProject;
    /**
     * 计算发货时间
     */
    private Date planTime;
    /**
     * 出库时间
     */
    private Date outboundTime;
    /**
     * 出库情况
     */
    private Byte outboundStatus;

}
