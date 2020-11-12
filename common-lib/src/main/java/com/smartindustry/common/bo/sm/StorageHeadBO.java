package com.smartindustry.common.bo.sm;

import com.smartindustry.common.pojo.sm.StorageHeadPO;
import lombok.Data;

import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 20:03 2020/10/26
 * @version: 1.0.0
 * @description:
 */
@Data
public class StorageHeadBO extends StorageHeadPO {

    private String warehouseName;

    private Byte payMethod;

    private String supplierName;

    private List<StorageBodyBO> bos;

    private List<WarehouseBO> warehouseBOS;
}
