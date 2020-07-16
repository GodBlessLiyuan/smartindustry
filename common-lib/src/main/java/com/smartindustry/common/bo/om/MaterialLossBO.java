package com.smartindustry.common.bo.om;

import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:35 2020/7/15
 * @version: 1.0.0
 * @description: 欠料列表实体类
 */
@Data
public class MaterialLossBO {
    private static final long SerialVersionUID = 1L;
    /**
     * 物料编号
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
    /**
     * 需求数量
     */
    private Integer demandNum;
    /**
     * 已扫描数量
     */
    private Integer pickNum;
}
