package com.smartindustry.common.bo.si;

import com.smartindustry.common.pojo.si.PrintLabelPO;
import lombok.Data;

/**
 * @author: xiahui
 * @date: Created in 2020/7/3 9:17
 * @description: 打印标签 BO
 * @version: 1.0
 */
@Data
public class PrintLabelBO extends PrintLabelPO {
    /**
     * 物流编码
     */
    private String materialNo;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 物料描述
     */
    private String materialDesc;
}
