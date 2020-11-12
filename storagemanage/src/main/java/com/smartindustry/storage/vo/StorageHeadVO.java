package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.sm.StorageBodyBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 20:24 2020/10/26
 * @version: 1.0.0
 * @description:
 */
@Data
public class StorageHeadVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 入库单表头id
     */
    private Long shid;
    /**
     * 入库单编号
     */
    private String sno;
    /**
     * 来源单号-生产工单
     */
    private String sono;
    /**
     * 入库仓库id
     */
    private Long wid;
    /**
     * 入库仓库
     */
    private String wname;
    /**
     * 单据状态
     */
    private Byte status;
    /**
     * 入库时间
     */
    private Date stime;
    /**
     * 付款方式
     */
    private Byte pmethod;
    /**
     * 备注
     */
    private String extra;

    /**
     * 供应商名称
     */
    private String sname;
    /**
     * 供应商id
     */
    private Long sid;

    private List<StorageBodyVO> vos;

    @Data
    public static class StorageBodyVO {
        /**
         * 表体id
         */
        private Long sbid;
        /**
         * 物料id
         */
        private Long mid;
        /**
         * 物料编号
         */
        private String mno;
        /**
         * 物料名称
         */
        private String mname;
        /**
         * 物料型号
         */
        private String mmodel;
        /**
         * 供应商名称
         */
        private String sname;
        /**
         * 车牌信息
         */
        private String cbrand;
        /**
         * 接受数量
         */
        private BigDecimal anum;
        /**
         * 接受时间
         */
        private Date atime;
        /**
         * 储位id
         */
        private Long lid;
        /**
         * 入库储位
         */
        private String lno;
        /**
         * 物料计量单位
         */
        private String muname;
        /**
         * 单价
         */
        private BigDecimal up;
        /**
         * 金额
         */
        private BigDecimal sp;
        /**
         * 不含税单价
         */
        private BigDecimal upn;
        /**
         * 不含税金额
         */
        private BigDecimal spn;

    }

    public static List<StorageHeadVO> convert(List<StorageHeadBO> bos) {
        List<StorageHeadVO> vos = new ArrayList<>(bos.size());
        for (StorageHeadBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static StorageHeadVO convert(StorageHeadBO bo) {
        StorageHeadVO vo = new StorageHeadVO();
        vo.setShid(bo.getStorageHeadId());
        vo.setSno(bo.getStorageNo());
        vo.setSono(bo.getSourceNo());
        vo.setStatus(bo.getStatus());
        vo.setStime(bo.getStorageTime());
        vo.setWname(bo.getWarehouseName());
        return vo;
    }

    public static StorageHeadVO convertVO(StorageHeadBO bo) {
        StorageHeadVO vo = new StorageHeadVO();
        vo.setShid(bo.getStorageHeadId());
        vo.setSno(bo.getStorageNo());
        vo.setStatus(bo.getStatus());
        vo.setStime(bo.getStorageTime());
        vo.setSid(bo.getSupplierId());
        vo.setSname(bo.getSupplierName());
        vo.setWid(bo.getWarehouseId());
        vo.setWname(bo.getWarehouseName());
        vo.setPmethod(bo.getPayMethod());
        vo.setExtra(bo.getRemark());
        List<StorageBodyVO> vos = new ArrayList<>();
        for (StorageBodyBO bodyBO : bo.getBos()){
            StorageBodyVO bodyVO = new StorageBodyVO();
            bodyVO.setSbid(bodyBO.getStorageBodyId());
            bodyVO.setMid(bodyBO.getMaterialId());
            bodyVO.setMno(bodyBO.getMaterialNo());
            bodyVO.setMname(bodyBO.getMaterialName());
            bodyVO.setMmodel(bodyBO.getMaterialModel());
            bodyVO.setSname(bodyBO.getSupplierName());
            bodyVO.setCbrand(bodyBO.getCarBrand());
            bodyVO.setAnum(bodyBO.getStorageNum());
            bodyVO.setAtime(bodyBO.getAcceptTime());
            bodyVO.setLid(bodyBO.getLocationId());
            bodyVO.setLno(bodyBO.getLocationNo());
            bodyVO.setMuname(bodyBO.getMeasureUnitName());
            bodyVO.setUp(bodyBO.getUnitPrice());
            bodyVO.setUpn(bodyBO.getUnitPriceNotax());
            bodyVO.setSp(bodyBO.getSumPrice());
            bodyVO.setSpn(bodyBO.getSumPriceNotax());
            vos.add(bodyVO);
        }
        vo.setVos(vos);
        return vo;
    }
}
