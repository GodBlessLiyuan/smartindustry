package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.si.WarehouseBO;
import com.smartindustry.common.pojo.si.LocationPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:48 2020/10/27
 * @version: 1.0.0
 * @description:
 */
@Data
public class WarehouseVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 仓库id
     */
    private Long value;

    /**
     * 仓库名称
     */
    private String label;
    /**
     * 当前仓库所包含的所有库位信息
     */
    private List<LocationVO> children;

    @Data
    public static class LocationVO {
        /**
         * 储位id
         */
        private Long value;
        /**
         * 储位名称
         */
        private String label;
    }

    public static List<WarehouseVO> convert(List<WarehouseBO> bos) {
        List<WarehouseVO> vos = new ArrayList<>(bos.size());
        for (WarehouseBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static WarehouseVO convert(WarehouseBO bo) {
        WarehouseVO vo = new WarehouseVO();
        vo.setValue(bo.getWarehouseId());
        vo.setLabel(bo.getWarehouseName());
        if (null != bo.getPos() && bo.getPos().size() > 0) {
            List<LocationVO> vos = new ArrayList<>(bo.getPos().size());
            for (LocationPO po : bo.getPos()) {
                LocationVO locationVO = new LocationVO();
                locationVO.setValue(po.getLocationId());
                locationVO.setLabel(po.getLocationNo());
                vos.add(locationVO);
            }
            vo.setChildren(vos);
        }
        return vo;
    }
}
