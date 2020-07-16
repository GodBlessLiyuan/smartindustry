package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.si.PrintLabelPO;
import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:04 2020/7/15
 * @version: 1.0.0
 * @description: 扫码拣货检索列表
 */
@Data
public class ScanPickBO extends PrintLabelPO {
    private static final long SerialVersionUID = 1L;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 物料描述
     */
    private String materialDesc;

}
