package com.smartindustry.common.bo.im;

import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.pojo.im.MaterialInventoryPO;
import lombok.Data;

import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/8/12 11:23
 * @description: 物料库存信息
 * @version: 1.0
 */
@Data
public class MaterialInventoryBO extends MaterialInventoryPO {
    private String materialNo;
    private String materialName;
    private Byte materialType;
    private String materialModel;
    private String materialDesc;
    private String supplierName;
    private List<LocationBO> locations;
    private Integer lowerLimit;
    private Byte status;
    private Integer availableNum;
}
