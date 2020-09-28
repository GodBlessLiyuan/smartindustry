package com.smartindustry.inventory.vo;

import com.smartindustry.common.bo.si.StorageLabelBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/8/13 9:42
 * @description: 物料库存明细
 * @version: 1.0
 */
@Data
public class StorageLabelVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long slid;
    private String pid;
    private String mno;
    private String mname;
    private String pbatch;
    private Byte mtype;
    private String mmodel;
    private String sname;
    private Date stime;
    private Byte mstatus;
    private String mlname;
    private Byte status;
    private Byte rsale;
    private String wname;
    private String lno;
    private Integer mnum;
    private String mnummunit;

    private String cname;

    public static List<StorageLabelVO> convert(List<StorageLabelBO> bos) {
        List<StorageLabelVO> vos = new ArrayList<>(bos.size());
        for (StorageLabelBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static StorageLabelVO convert(StorageLabelBO bo) {
        StorageLabelVO vo = new StorageLabelVO();
        vo.setSlid(bo.getStorageLabelId());
        vo.setPid(bo.getPackageId());
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setPbatch(bo.getProduceBatch());
        vo.setMtype(bo.getMaterialType());
        vo.setMmodel(bo.getMaterialModel());
        vo.setSname(bo.getSupplierName());
        vo.setStime(bo.getStorageTime());
        vo.setMstatus((byte) (null == bo.getMaterialLockId() ? 1 : 2));
        vo.setMlname(bo.getMaterialLockName());
        vo.setStatus(bo.getStatus());
        vo.setRsale((byte) 2);
        vo.setWname(bo.getWarehouseName());
        vo.setLno(bo.getLocationNo());
        vo.setMnum(bo.getStorageNum());
        if (bo.getStorageNum() != null) {
            vo.setMnummunit(bo.getStorageNum()+" "+(bo.getMeasureUnitName() != null?bo.getMeasureUnitName():""));
        }
        vo.setCname(null);
        return vo;
    }
}
