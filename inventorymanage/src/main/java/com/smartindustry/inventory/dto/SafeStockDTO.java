package com.smartindustry.inventory.dto;

import com.smartindustry.common.pojo.si.MaterialInventoryPO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:29 2020/11/11
 * @version: 1.0.0
 * @description:
 */
@Data
public class SafeStockDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 安全库存物料列表
     */
    private Set<Long> mids;
    /**
     * 设置安全下限
     */
    private BigDecimal lower;
    /**
     * 设置安全上限
     */
    private BigDecimal upper;

    public static List<MaterialInventoryPO> createPOS(SafeStockDTO dto) {
        List<MaterialInventoryPO> pos = new ArrayList<>();
        for(Long mid: dto.getMids()){
            MaterialInventoryPO po = new MaterialInventoryPO();
            po.setMaterialId(mid);
            po.setLowerLimit(dto.getLower());
            po.setUpperLimit(dto.getUpper());
            pos.add(po);
        }
        return pos;
    }
}
