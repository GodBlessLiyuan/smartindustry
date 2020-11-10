package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.om.OutboundRecordPO;
import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:46 2020/11/10
 * @version: 1.0.0
 * @description:
 */
@Data
public class OutboundRecordBO extends OutboundRecordPO {
    /**
     * 操作人名称/也指叉车名称/系统特代
     */
    private String name;
}
