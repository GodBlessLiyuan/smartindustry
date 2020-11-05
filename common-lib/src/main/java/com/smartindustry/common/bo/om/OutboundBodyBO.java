package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.om.OutboundBodyPO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:25 2020/10/28
 * @version: 1.0.0
 * @description:
 */
@Data
public class OutboundBodyBO extends OutboundBodyPO {
    private String materialNo;

    private String materialName;

    private String materialModel;

    private String measureUnitName;
}
