package com.smartindustry.common.bo.si;

import com.smartindustry.common.pojo.si.StorageLabelPO;
import lombok.Data;

/**
 * @author: xiahui
 * @date: Created in 2020/7/28 16:19
 * @description: 库位标签
 * @version: 1.0
 */
@Data
public class StorageLabelBO extends StorageLabelPO {
    private String locationNo;
}
