package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.si.LocationPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:26
 * @description: 货位管理
 * @version: 1.0
 */
@Data
public class LocationDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long lid;
    private String lno;
    private String lname;
    private Long ltid;
    private Long wid;
    private String remark;

    public static LocationPO createPO(LocationDTO dto) {
        LocationPO po = new LocationPO();
        po.setCreateTime(new Date());
        po.setDr((byte) 1);
        return buildPO(po, dto);
    }

    public static LocationPO buildPO(LocationPO po, LocationDTO dto) {
        po.setLocationNo(dto.getLno());
        po.setLocationName(dto.getLname());
        po.setLocationTypeId(dto.getLtid());
        po.setWarehouseId(dto.getWid());
        po.setRemark(dto.getRemark());
        return po;
    }
}
