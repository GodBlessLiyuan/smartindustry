package com.smartindustry.common.bo.sm;

import com.smartindustry.common.pojo.si.LocationPO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:44 2020/11/10
 * @version: 1.0.0
 * @description:
 */
@Data
public class LocationDetailBO extends LocationPO{
    /**
     * 栈板rfid
     */
    private String rfid;
    /**
     * 仓库名称
     */
    private String warehouseName;

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
    private BigDecimal packageVolume;

    /**
     * 单位名称
     */
    private String measureUnitName;
}
