package com.smartindustry.common.bo.sm.bo.si;

import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.si.WarehousePO;
import lombok.Data;

import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:53
 * @description: 仓库
 * @version: 1.0
 */
@Data
public class WarehouseBO extends WarehousePO {
    private String warehouseTypeName;

    List<LocationPO> pos;
}
