package com.smartindustry.common.bo.sm;

import com.smartindustry.common.pojo.sm.StorageDetailPO;
import lombok.Data;

/**
 * @author: xiahui
 * @date: Created in 2020/7/7 10:59
 * @description: 物料入库详情
 * @version: 1.0
 */
@Data
public class StorageDetailBO extends StorageDetailPO {

    private String packageId;
    private Long materialId;
    private String materialNo;
    private String materialDesc;
    private Integer num;
}
