package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.SupplierGroupPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 14:31
 * @description: 供应商组
 * @version: 1.0
 */
@Data
public class SupplierGroupDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long sgid;
    private String sgname;

    public static SupplierGroupPO createPO(SupplierGroupDTO dto, Long userId) {
        SupplierGroupPO po = new SupplierGroupPO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static SupplierGroupPO buildPO(SupplierGroupPO po, SupplierGroupDTO dto) {
        po.setSupplierGroupName(dto.getSgname());
        return po;
    }
}
