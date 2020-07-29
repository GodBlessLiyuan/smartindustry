package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.WarehouseTypePO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 16:05
 * @description: 仓库类型
 * @version: 1.0
 */
@Data
public class WarehouseTypeDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long wtid;
    private String wtname;

    public static WarehouseTypePO createPO(WarehouseTypeDTO dto, Long userId) {
        WarehouseTypePO po = new WarehouseTypePO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static WarehouseTypePO buildPO(WarehouseTypePO po, WarehouseTypeDTO dto) {
        po.setWarehouseTypeName(dto.getWtname());
        return po;
    }
}
