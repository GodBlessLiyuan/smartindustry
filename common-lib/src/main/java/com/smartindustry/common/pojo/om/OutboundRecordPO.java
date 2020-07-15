package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * om_outbound_record
 * @author 
 */
@Data
public class OutboundRecordPO implements Serializable {
    private Long recordId;

    private Long pickHeadId;

    private Long userId;

    private String name;

    private String type;

    private Date createTime;

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
    private Byte status;

    private static final long serialVersionUID = 1L;
}