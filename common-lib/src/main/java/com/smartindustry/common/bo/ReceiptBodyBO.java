package com.smartindustry.common.bo;

import com.smartindustry.common.pojo.ReceiptBodyPO;
import lombok.Data;

/**
 * @author: xiahui
 * @date: Created in 2020/7/2 10:43
 * @description: 收料表体 BO
 * @version: 1.0
 */
@Data
public class ReceiptBodyBO extends ReceiptBodyPO {

    private String materialName;
    private Byte materialType;
    private String materialModel;
    private String materialDesc;
    private Byte testType;
}
