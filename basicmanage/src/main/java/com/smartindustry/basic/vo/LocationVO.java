package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.LocationBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:27
 * @description: 货位管理
 * @version: 1.0
 */
@Data
public class LocationVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long lid;
    private String lno;
    private String lname;
    private Long wid;
    private String wname;
    private String remark;

    public static List<LocationVO> convert(List<LocationBO> bos) {
        List<LocationVO> vos = new ArrayList<>(bos.size());
        for (LocationBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static LocationVO convert(LocationBO bo) {
        LocationVO vo = new LocationVO();
        vo.setLid(bo.getLocationId());
        vo.setLno(bo.getLocationNo());
        vo.setLname(bo.getLocationName());
        vo.setWid(bo.getWarehouseId());
        vo.setWname(bo.getWarehouseName());
        vo.setRemark(bo.getRemark());
        return vo;
    }
}
