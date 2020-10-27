package com.smartindustry.common.bo.sm;

import com.smartindustry.common.pojo.sm.StorageBodyPO;
import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:22 2020/10/27
 * @version: 1.0.0
 * @description:
 */
@Data
public class StorageBodyBO extends StorageBodyPO {

    private String materialNo;

    private String materialName;

    private String materialModel;

    private String supplierName;

    private String locationNo;
}
