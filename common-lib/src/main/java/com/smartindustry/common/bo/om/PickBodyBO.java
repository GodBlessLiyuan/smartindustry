package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.om.PickBodyPO;
import lombok.Data;

/**
 * @author: xiahui
 * @date: Created in 2020/7/17 15:11
 * @description: 拣货单表体 BO
 * @version: 1.0
 */
@Data
public class PickBodyBO extends PickBodyPO {
    /**
     * 物料编号
     */
    private String materialNo;
    /**
     * 物料描述
     */
    private String materialDesc;
}
