package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.si.MaterialPO;
import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:35 2020/7/15
 * @version: 1.0.0
 * @description: 欠料列表实体类
 */
@Data
public class MaterialBO extends MaterialPO {
    private static final long SerialVersionUID = 1L;
    /**
     * 物料编号
     */
    private String pickNo;
    /**
     * 需求数量
     */
    private Integer demandNum;
    /**
     * 已扫描数量
     */
    private Integer pickNum;
}
