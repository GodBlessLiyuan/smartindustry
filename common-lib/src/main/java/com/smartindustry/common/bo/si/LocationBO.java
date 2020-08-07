package com.smartindustry.common.bo.si;

import com.smartindustry.common.pojo.si.LocationPO;
import lombok.Data;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 11:40
 * @description: 库位管理
 * @version: 1.0
 */
@Data
public class LocationBO extends LocationPO {
    private String locationTypeName;
    private String warehouseName;
}
