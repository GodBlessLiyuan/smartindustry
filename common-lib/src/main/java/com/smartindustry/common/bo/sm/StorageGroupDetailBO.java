package com.smartindustry.common.bo.sm;

import com.smartindustry.common.pojo.sm.StorageGroupPO;
import lombok.Data;

import java.util.List;

/**
 * @author  feng.hui
 * @since  2020-09-17 15:53
 * @version  1.0
 * @description 查询入库单详情已入库详情使用的
 */
@Data
public class StorageGroupDetailBO extends StorageGroupPO {
    private Long materialId;
    private String materialNo;
    private String materialName;
    private Integer num;
    List<StorageDetailBO> detail;
}
