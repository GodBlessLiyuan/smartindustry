package com.smartindustry.common.bo.sm;

import com.smartindustry.common.pojo.sm.ReceiptBodyPO;
import lombok.Data;

/**
 * @author: xiahui
 * @date: Created in 2020/7/2 10:43
 * @description: 收料表体 BO
 * @version: 1.0
 */
@Data
public class ReceiptBodyBO extends ReceiptBodyPO {

    private String materialNo;
    private Byte materialType;
    private String materialName;
    private String materialModel;
    private String materialDesc;
    private Byte testType;
}
