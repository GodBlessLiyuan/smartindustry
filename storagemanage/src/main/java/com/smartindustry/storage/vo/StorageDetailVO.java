package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.sm.LocationBO;
import com.smartindustry.common.bo.sm.MaterialBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.bo.sm.WarehouseBO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 8:40 2020/11/6
 * @version: 1.0.0
 * @description:
 */
@Data
public class StorageDetailVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 入库单表头id
     */
    private Long shid;
    /**
     * 入库单表头
     */
    private String sno;
    /**
     * 来源工单/生产工单
     */
    private String sono;
    /**
     * 入库时间
     */
    private Date stime;

    private List<WarehouseVO> vos;

    @Data
    public static class WarehouseVO {
        /**
         * 仓库id
         */
        private Long wid;
        /**
         * 仓库名称
         */
        private String wname;

        private List<LocationVO> vos;
    }

    @Data
    public static class LocationVO {
        /**
         * 储位id
         */
        private Long lid;
        /**
         * 储位名称
         */
        private String lno;

        private List<RfidVO> vos;
    }

    @Data
    public static class RfidVO {
        /**
         * 物料id
         */
        private Long mid;
        /**
         * 物料编码
         */
        private String mno;
        /**
         * 物料名称
         */
        private String mname;
        /**
         * 栈板数
         */
        private Integer num;
        /**
         * 数量/立方米
         */
        private BigDecimal volume;
    }

    public static StorageDetailVO convert(StorageHeadBO bo) {
        StorageDetailVO vo = new StorageDetailVO();
        vo.setShid(bo.getStorageHeadId());
        vo.setSno(bo.getStorageNo());
        vo.setSono(bo.getSourceNo());
        vo.setStime(bo.getStorageTime());
        List<WarehouseVO> warehouseVOS = new ArrayList<>();
        for(WarehouseBO warehouseBO : bo.getWarehouseBOS()){
            WarehouseVO warehouseVO = new WarehouseVO();
            warehouseVO.setWid(warehouseBO.getWarehouseId());
            warehouseVO.setWname(warehouseBO.getWarehouseName());
            List<LocationVO> locationVOS = new ArrayList<>();
            for(LocationBO locationBO : warehouseBO.getLocationBOS()){
                LocationVO locationVO = new LocationVO();
                locationVO.setLid(locationBO.getLocationId());
                locationVO.setLno(locationBO.getLocationNo());
                List<RfidVO> rfidVOS = new ArrayList<>();
                for(MaterialBO materialBO : locationBO.getMaterialBOS()){
                    RfidVO rfidVO = new RfidVO();
                    rfidVO.setMid(materialBO.getMaterialId());
                    rfidVO.setMname(materialBO.getMaterialName());
                    rfidVO.setMno(materialBO.getMaterialNo());
                    rfidVO.setNum(materialBO.getNum());
                    rfidVO.setVolume(new BigDecimal(materialBO.getNum()).multiply(materialBO.getVolume()));
                    rfidVOS.add(rfidVO);
                }
            }
        }

        return vo;
    }
}
