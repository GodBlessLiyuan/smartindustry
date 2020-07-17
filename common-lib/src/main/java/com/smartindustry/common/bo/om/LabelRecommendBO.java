package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.om.LabelRecommendPO;
import lombok.Data;

/**
 * @author: xiahui
 * @date: Created in 2020/7/17 15:42
 * @description: 标签推荐
 * @version: 1.0
 */
@Data
public class LabelRecommendBO extends LabelRecommendPO {
    /**
     * 库位编号
     */
    private String locationNo;
    /**
     * PID
     */
    private String packageId;
}
