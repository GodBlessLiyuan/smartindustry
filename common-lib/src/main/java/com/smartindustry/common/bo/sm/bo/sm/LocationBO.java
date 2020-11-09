package com.smartindustry.common.bo.sm.bo.sm;

import com.smartindustry.common.pojo.si.LocationPO;
import lombok.Data;

import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:23 2020/11/4
 * @version: 1.0.0
 * @description:
 */
@Data
public class LocationBO extends LocationPO {
    /**
     * 储位下存储的物料详情统计
     */
    List<MaterialBO> materialBOS;
}
