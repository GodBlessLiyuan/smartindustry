package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.si.MaterialAttributePO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: xiahui
 * @date: Created in 2020/9/16 10:20
 * @description: 物料属性
 * @version: 1.0
 */
@Data
public class MaterialAttributeDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private BigDecimal llimit;
    private BigDecimal ulimit;
    private BigDecimal dpurchase;
    private Byte way;
    private Long wid;
    private Long lid;
    private Byte sinspect;
    private Byte sitype;
    private Byte ssplan;
    private Byte oinspect;
    private Byte psplit;

    public static MaterialAttributePO createPO(MaterialAttributeDTO dto) {
        return buildPO(new MaterialAttributePO(), dto);
    }

    public static MaterialAttributePO buildPO(MaterialAttributePO po, MaterialAttributeDTO dto) {
        po.setLowerLimit(dto.getLlimit());
        po.setUpperLimit(dto.getUlimit());
        po.setDefaultPurchase(dto.getDpurchase());
        po.setWay(dto.getWay());
        po.setWarehouseId(dto.getWid());
        po.setLocationId(dto.getLid());
        po.setStorageInspect(dto.getSinspect());
        po.setStorageInspectType(dto.getSitype());
        po.setStorageSamplingPlan(dto.getSsplan());
        po.setOutboundInspect(dto.getOinspect());
        po.setPickSplit(dto.getPsplit());
        return po;
    }
}
