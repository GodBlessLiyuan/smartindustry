package com.smartindustry.common.bo.sm;

import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.pojo.sm.StoragePO;
import lombok.Data;

import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/2 19:45
 * @description: 物料入库 BO
 * @version: 1.0
 */
@Data
public class StorageBO extends StoragePO {

    /**
     * 收料单表体ID
     */
    private Long receiptBodyId;

    /**
     * 收料单号
     */
    private String receiptNo;
    /**
     * 来源单号
     */
    private String receiptSourceNo;
    /**
     * 来源类型
     */
    private Byte receiptSourceType;
    /**
     * 仓库名称
     */
    private List<LocationBO> locations;

    /**
     * 对应单号
     */
    private String correspondNo;

    /**
     * 入库类型（调拨订单类型）
     */
    private Byte transferType;

    /**
     * 当前入库单是否存在库位id
     */
    private Long locationId;

    /**
     * 拣货工单号
     */
    private String pickNo;
}
