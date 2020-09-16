package com.smartindustry.common.bo.si;

import com.smartindustry.common.pojo.si.MaterialAttributePO;
import lombok.Data;

/**
 * @author: xiahui
 * @date: Created in 2020/9/16 14:25
 * @description: TODO
 * @version: 1.0
 */
@Data
public class MaterialAttributeBO extends MaterialAttributePO {
    private String warehouseName;
    private String locationName;
}
