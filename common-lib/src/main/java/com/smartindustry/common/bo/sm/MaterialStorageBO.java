package com.smartindustry.common.bo.sm;

import com.smartindustry.common.pojo.sm.MaterialStoragePO;
import lombok.Data;

/**
 * @author: xiahui
 * @date: Created in 2020/7/2 19:45
 * @description: 物料入库 BO
 * @version: 1.0
 */
@Data
public class MaterialStorageBO extends MaterialStoragePO {

    /**
     * 收料单号
     */
    private String receiptNo;
    /**
     * 收料类型
     */
    private Byte orderType;
    /**
     * 物料编码
     */
    private String materialNo;
    /**
     * 物料类型
     */
    private Byte materialType;
    /**
     * 物料描述
     */
    private String materialDesc;
}
