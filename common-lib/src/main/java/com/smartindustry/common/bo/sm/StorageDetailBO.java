package com.smartindustry.common.bo.sm;

import com.smartindustry.common.pojo.sm.StorageDetailPO;
import lombok.Data;

import java.util.List;

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
    private String materialName;
    private String materialDesc;
    private String locationNo;
    private String warehouseName;
    private Integer num;
    private Long storageDetailId;

    private Long storageGroupId;

    private Long printLabelId;

    /**
     * 表示同一库位下的标签列表
     */
    private List<StorageDetailBO> labels;

}
