package com.smartindustry.inventory.vo;

import com.smartindustry.common.bo.im.MaterialInventoryBO;
import com.smartindustry.common.bo.si.LocationBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/8/11 15:40
 * @description: 物料库存统计
 * @version: 1.0
 */
@Data
public class MaterialInventoryVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long miid;
    private Long mid;
    private String mno;
    private String mname;
    private Byte mtype;
    private String mmodel;
    private String mdesc;
    private String supplier;
    private String warehouse;
    private Integer wnum;
    private Integer llimit;
    private Byte status;
    private Integer lnum;
    private Integer rnum;
    private Integer anum;

    public static List<MaterialInventoryVO> convert(List<MaterialInventoryBO> bos) {
        List<MaterialInventoryVO> vos = new ArrayList<>(bos.size());
        for (MaterialInventoryBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static MaterialInventoryVO convert(MaterialInventoryBO bo) {
        MaterialInventoryVO vo = new MaterialInventoryVO();

        vo.setMiid(bo.getMaterialInventoryId());
        vo.setMid(bo.getMaterialId());
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setMtype(bo.getMaterialType());
        vo.setMmodel(bo.getMaterialModel());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setSupplier(bo.getSupplierName());
        StringBuilder sb = new StringBuilder();
        for (LocationBO lbo : bo.getLocations()) {
            sb.append(lbo.getWarehouseName()).append(" ");
        }
        vo.setWarehouse(sb.toString());
        vo.setWnum(bo.getWayNum());
        vo.setLlimit(bo.getLowerLimit());
        vo.setStatus(bo.getStatus());
        vo.setLnum(bo.getLockNum());
        vo.setRnum(bo.getRelateNum());
        vo.setAnum(bo.getAvailableNum());

        return vo;
    }
}
