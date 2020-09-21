package com.smartindustry.storage.vo;

import com.smartindustry.common.pojo.si.LocationPO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hui.feng
 * @date created in 2020/9/18
 * @description
 */
@Data
public class LocationVO implements Serializable {

    private Long lid;

    private String lno;

    private String lname;

    private Long whid;

    public static LocationVO convert(LocationPO po) {
        LocationVO vo = new LocationVO();
        vo.setLid(po.getLocationId());
        vo.setLname(po.getLocationName());
        vo.setLno(po.getLocationNo());
        vo.setWhid(po.getWarehouseId());
        return vo;
    }

}
