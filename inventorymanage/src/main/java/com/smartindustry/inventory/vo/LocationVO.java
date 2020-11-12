package com.smartindustry.inventory.vo;

import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.si.WarehousePO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:10 2020/11/12
 * @version: 1.0.0
 * @description:
 */
@Data
public class LocationVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 仓库id
     */
    private Long lid;
    /**
     * 仓库名称
     */
    private String lname;

    public static List<LocationVO> convert(List<LocationPO> pos) {
        List<LocationVO> vos = new ArrayList<>(pos.size());
        for (LocationPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static LocationVO convert(LocationPO po) {
        LocationVO vo = new LocationVO();
        vo.setLid(po.getLocationId());
        vo.setLname(po.getLocationName());
        return vo;
    }
}
