package com.smartindustry.inventory.dto;

import com.smartindustry.common.pojo.im.SafeStockPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/8/13 14:54
 * @description: 安全库存 DTO
 * @version: 1.0
 */
@Data
public class SafeStockDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long ssid;
    private Long miid;
    private Long mid;
    private Integer llimit;
    private Byte way;

    public static SafeStockPO createPO(SafeStockDTO dto) {
        SafeStockPO po = new SafeStockPO();
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static SafeStockPO buildPO(SafeStockPO po, SafeStockDTO dto) {
        po.setMaterialInventoryId(dto.getMiid());
        po.setLowerLimit(dto.getLlimit());
        po.setWay(dto.getWay());
        return po;
    }
}
