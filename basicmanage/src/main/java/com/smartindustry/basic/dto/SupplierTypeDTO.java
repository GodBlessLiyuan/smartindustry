package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.SupplierTypePO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 14:36
 * @description: 供应商类型
 * @version: 1.0
 */
@Data
public class SupplierTypeDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long stid;
    private String stname;

    public static SupplierTypePO createPO(SupplierTypeDTO dto, Long userId) {
        SupplierTypePO po = new SupplierTypePO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static SupplierTypePO buildPO(SupplierTypePO po, SupplierTypeDTO dto) {
        po.setSupplierTypeName(dto.getStname());
        return po;
    }
}
