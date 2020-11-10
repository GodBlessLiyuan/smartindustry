package com.smartindustry.common.bo.sm;

import com.smartindustry.common.pojo.si.MaterialPO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:19 2020/11/4
 * @version: 1.0.0
 * @description: 物料详情统计
 */
@Data
public class MaterialBO extends MaterialPO {
    /**
     * 栈板数
     */
    private Integer num;
    /**
     * 体积数量
     */
    private BigDecimal volume;

    private String measureUnitName;
}
