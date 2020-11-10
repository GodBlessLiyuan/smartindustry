package com.smartindustry.common.bo.sm;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:29 2020/11/4
 * @version: 1.0.0
 * @description:
 */
@Data
public class MaterialDetailBO {
    /**
     * 物料id
     */
    private Long materialId;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 物料编号
     */
    private String materialNo;
    /**
     * 栈板合计总数
     */
    private Integer total;
    /**
     * 1 栈板的体积
     */
    private BigDecimal packageVolume;
    /**
     * 单位名称
     */
    private String measureUnitName;

    private List<LocationDetailBO> bos;
}
