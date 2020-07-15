package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import lombok.Data;

/**
 * om_label_recommend
 * @author 
 */
@Data
public class LabelRecommendPO implements Serializable {
    private Long labelRecommendId;

    private Long pickBodyId;

    private Long storageMaterialId;

    private static final long serialVersionUID = 1L;
}