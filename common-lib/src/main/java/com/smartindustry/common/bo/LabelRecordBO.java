package com.smartindustry.common.bo;

import com.smartindustry.common.pojo.LabelRecordPO;
import lombok.Data;

/**
 * @author: xiahui
 * @date: Created in 2020/7/8 16:35
 * @description: 标签打印记录
 * @version: 1.0
 */
@Data
public class LabelRecordBO extends LabelRecordPO {
    /**
     * packageID
     */
    private String packageId;
    /**
     * 打印物料数量
     */
    private Integer num;
    /**
     * 关联的packageID
     */
    private String relatePackageId;
}
