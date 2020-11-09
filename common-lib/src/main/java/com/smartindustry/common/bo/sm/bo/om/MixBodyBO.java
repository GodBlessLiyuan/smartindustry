package com.smartindustry.common.bo.sm.bo.om;

import com.smartindustry.common.pojo.wo.SlurryMaterialPO;
import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:27 2020/10/28
 * @version: 1.0.0
 * @description:
 */
@Data
public class MixBodyBO extends SlurryMaterialPO {

    private String materialNo;

    private String materialName;

    private String materialModel;

    private String measureUnitName;
}
