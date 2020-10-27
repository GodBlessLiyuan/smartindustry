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
    private Long wid;
    /**
     * 仓库编号
     */
    private String wno;
    /**
     * 仓库名称
     */
    private String wname;
    /**
     * 当前仓库所包含的所有库位信息
     */
    private List<LocationVO> vos;

    @Data
    public static class LocationVO {
        /**
         * 储位id
         */
        private Long lid;
        /**
         * 储位编号
         */
        private String lno;
        /**
         * 储位名称
         */
        private String lname;
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
        vo.setWid(bo.getWarehouseId());
        vo.setWno(bo.getWarehouseNo());
        vo.setWname(bo.getWarehouseName());
        if (null != bo.getPos() && bo.getPos().size() > 0) {
            List<LocationVO> vos = new ArrayList<>(bo.getPos().size());
            for (LocationPO po : bo.getPos()) {
                LocationVO locationVO = new LocationVO();
                locationVO.setLid(po.getLocationId());
                locationVO.setLno(po.getLocationNo());
                locationVO.setLname(po.getLocationName());
                vos.add(locationVO);
            }
            vo.setVos(vos);
        }
        return vo;
    }
}
