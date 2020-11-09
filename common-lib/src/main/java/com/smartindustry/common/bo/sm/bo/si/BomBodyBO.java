package com.smartindustry.common.bo.sm.bo.si;

import com.smartindustry.common.pojo.si.BomBodyPO;
import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:16 2020/8/14
 * @version: 1.0.0
 * @description:
 */
@Data
public class BomBodyBO extends BomBodyPO {
    private String materialNo;

    private String materialName;

    private String materialModel;

    private String materialPropertyName;

    private String supplierName;

    private String measureUnitName;

    private String processName;

    private String parentName;
}
