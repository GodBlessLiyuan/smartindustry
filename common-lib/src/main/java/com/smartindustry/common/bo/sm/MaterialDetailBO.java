package com.smartindustry.common.bo.sm;

import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:29 2020/11/4
 * @version: 1.0.0
 * @description:
 */
@Data
public class MaterialDetailBO {

    private String rfid;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 库位编号
     */
    private String locationNo;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 物料编号
     */
    private String materialNo;
    /**
     * 是否是备料区false还是成品区true
     */
    private Boolean flag;
    /**
     * 栈板显示 1 栈板
     */
    private String pallet;
    /**
     * 1 栈板的体积
     */
    private String volume;
}
