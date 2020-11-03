package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.om.MixBodyPO;
import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:27 2020/10/28
 * @version: 1.0.0
 * @description:
 */
@Data
public class MixBodyBO extends MixBodyPO {

    private String materialNo;

    private String materialName;

    private String materialModel;

    private String measureUnitName;
}
