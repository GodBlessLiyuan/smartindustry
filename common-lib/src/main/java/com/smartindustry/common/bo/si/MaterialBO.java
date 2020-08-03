package com.smartindustry.common.bo.si;

import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.si.MaterialSpecificationPO;
import lombok.Data;

import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 18:35
 * @description: 物料 BO
 * @version: 1.0
 */
@Data
public class MaterialBO extends MaterialPO {
    private static final long SerialVersionUID = 1L;

    private String materialTypeName;
    private String humidityLevelName;
    private String materialLevelName;
    private String measureUnitName;
    private String materialVersionName;
    private String produceLossLevelName;
    private String lifeCycleStateName;
    private String supplierName;
    private List<MaterialSpecificationPO> files;
}
