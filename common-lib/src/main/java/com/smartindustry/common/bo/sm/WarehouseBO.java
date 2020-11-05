package com.smartindustry.common.bo.sm;

import com.smartindustry.common.pojo.si.WarehousePO;
import lombok.Data;

import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:25 2020/11/4
 * @version: 1.0.0
 * @description:
 */
@Data
public class WarehouseBO extends WarehousePO {
    /**
     * 仓库下存放成品入库的储位信息
     */
    List<LocationBO> locationBOS;
}
