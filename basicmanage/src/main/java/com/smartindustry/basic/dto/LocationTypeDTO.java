package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.LocationTypePO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 16:05
 * @description: 货位类型
 * @version: 1.0
 */
@Data
public class LocationTypeDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long ltid;
    private String ltname;

    public static LocationTypePO createPO(LocationTypeDTO dto, Long userId) {
        LocationTypePO po = new LocationTypePO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static LocationTypePO buildPO(LocationTypePO po, LocationTypeDTO dto) {
        po.setLocationTypeName(dto.getLtname());
        return po;
    }
}
