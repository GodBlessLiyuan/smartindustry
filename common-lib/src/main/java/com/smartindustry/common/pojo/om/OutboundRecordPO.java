package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * om_outbound_record
 * @author 
 */
@AllArgsConstructor
@Data
public class OutboundRecordPO implements Serializable {
    private Long recordId;

    private Long pickHeadId;

    private Long outboundId;

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

    public OutboundRecordPO(Long pickHeadId,Long outboundId,Long userId,String name,String type,Date createTime,Byte status){
        this.pickHeadId = pickHeadId;
        this.outboundId = outboundId;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.createTime = createTime;
        this.status = status;
    }
}