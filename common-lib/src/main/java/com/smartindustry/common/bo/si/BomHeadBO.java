package com.smartindustry.common.bo.si;

import com.smartindustry.common.pojo.si.BomHeadPO;
import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:34 2020/8/13
 * @version: 1.0.0
 * @description:
 */
@Data
public class BomHeadBO extends BomHeadPO {
    /**
     * 主物料bom的物料明细id
     */
    private Long bomBodyId;

    /**
     * 物料id
     */
    private Long materialId;
    /**
     * 物料编码
     */
    private String materialNo;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 物料规格型号
     */
    private String materialModel;
    /**
     * 物料描述
     */
    private String materialDesc;
    /**
     * 物料层级
     */
    private String materialLevelName;
    /**
     * 属性名称
     */
    private String materialPropertyName;
}
