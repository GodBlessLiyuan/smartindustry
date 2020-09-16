package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.MaterialAttributeBO;
import com.smartindustry.common.pojo.si.MaterialAttributePO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: xiahui
 * @date: Created in 2020/9/16 14:18
 * @description: TODO
 * @version: 1.0
 */
@Data
public class MaterialAttributeVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private BigDecimal llimit;
    private BigDecimal ulimit;
    private BigDecimal dpurchase;
    private Boolean way;
    private Long wid;
    private String wname;
    private Long lid;
    private String lname;
    private Byte sinspect;
    private Byte sitype;
    private Byte ssplan;
    private Byte oinspect;
    private Boolean psplit;

    public static MaterialAttributeVO convert(MaterialAttributeBO bo) {
        MaterialAttributeVO vo = new MaterialAttributeVO();
        vo.setLlimit(bo.getLowerLimit());
        vo.setUlimit(bo.getUpperLimit());
        vo.setDpurchase(bo.getDefaultPurchase());
        vo.setWay(bo.getWay() == null || bo.getWay() == 2);
        vo.setWid(bo.getWarehouseId());
        vo.setWname(bo.getWarehouseName());
        vo.setLid(bo.getLocationId());
        vo.setLname(bo.getLocationName());
        vo.setSinspect(bo.getStorageInspect());
        vo.setSitype(bo.getStorageInspectType());
        vo.setSsplan(bo.getStorageSamplingPlan());
        vo.setOinspect(bo.getOutboundInspect());
        vo.setPsplit(bo.getPickSplit() == null || bo.getPickSplit() == 2);
        return vo;
    }
}
