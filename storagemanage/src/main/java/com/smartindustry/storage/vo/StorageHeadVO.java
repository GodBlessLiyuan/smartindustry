package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.sm.StorageBodyBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import lombok.Data;

import java.io.Serializable;
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
     * 备注
     */
    private String extra;

    private List<StorageBodyVO> vos;

    @Data
    public static class StorageBodyVO {
        /**
         * 表体id
         */
        private Long sbid;
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
        private Integer anum;
        /**
         * 接受时间
         */
        private Date atime;
        /**
         * 入库储位
         */
        private String lno;
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
        vo.setWname(bo.getWarehouseName());
        vo.setExtra(bo.getExtra());
        List<StorageBodyVO> vos = new ArrayList<>();
        for (StorageBodyBO bodyBO : bo.getBos()){
            StorageBodyVO bodyVO = new StorageBodyVO();
            bodyVO.setSbid(bodyBO.getStorageBodyId());
            bodyVO.setMno(bodyBO.getMaterialNo());
            bodyVO.setMname(bodyBO.getMaterialName());
            bodyVO.setMmodel(bodyBO.getMaterialModel());
            bodyVO.setSname(bodyBO.getSupplierName());
            bodyVO.setCbrand(bodyBO.getCarBrand());
            bodyVO.setAnum(bodyBO.getAcceptNum());
            bodyVO.setAtime(bodyBO.getAcceptTime());
            bodyVO.setLno(bodyBO.getLocationNo());
            vos.add(bodyVO);
        }
        vo.setVos(vos);
        return vo;
    }
}