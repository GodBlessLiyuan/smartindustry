package com.smartindustry.basic.vo;

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
     * 单据状态
     */
    private Byte status;
    /**
     * 入库时间
     */
    private Date stime;
    /**
     * 备注
     */
    private String extra;

    private Byte pmethod;

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
        private BigDecimal uprice;

        /**
         * 金额
         */
        private BigDecimal sprice;

        /**
         * 不含税单价
         */
        private BigDecimal upotax;

        /**
         * 不含税金额
         */
        private BigDecimal spntax;

        /**
         * 供应商ID
         */
        private Long sid;

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
        vo.setPmethod(bo.getPayMethod());
        return vo;
    }

    public static StorageHeadVO convertVO(StorageHeadBO bo) {
        StorageHeadVO vo = new StorageHeadVO();
        vo.setShid(bo.getStorageHeadId());
        vo.setSno(bo.getStorageNo());
        vo.setStatus(bo.getStatus());
        vo.setStime(bo.getStorageTime());
        vo.setExtra(bo.getExtra());
        List<StorageBodyVO> vos = new ArrayList<>();
        for (StorageBodyBO bodyBO : bo.getBos()){
            StorageBodyVO bodyVO = new StorageBodyVO();
            bodyVO.setSbid(bodyBO.getStorageBodyId());
            bodyVO.setMid(bodyBO.getMaterialId());
            bodyVO.setMno(bodyBO.getMaterialNo());
            bodyVO.setMname(bodyBO.getMaterialName());
            bodyVO.setMmodel(bodyBO.getMaterialModel());
            bodyVO.setSname(bodyBO.getSupplierName());
            bodyVO.setAnum(bodyBO.getStorageNum());
            bodyVO.setAtime(bodyBO.getAcceptTime());
            bodyVO.setLid(bodyBO.getLocationId());
            bodyVO.setLno(bodyBO.getLocationNo());
            bodyVO.setMuname(bodyBO.getMeasureUnitName());
            bodyVO.setUprice(bodyBO.getUnitPrice());
            bodyVO.setSprice(bodyBO.getSumPrice());
            bodyVO.setSpntax(bodyBO.getSumPriceNotax());
            bodyVO.setUpotax(bodyBO.getUnitPriceNotax());
            bodyVO.setSname(bodyBO.getSupplierName());
            bodyVO.setSid(bodyBO.getSupplierId());
            vos.add(bodyVO);
        }
        vo.setVos(vos);
        return vo;
    }
}
