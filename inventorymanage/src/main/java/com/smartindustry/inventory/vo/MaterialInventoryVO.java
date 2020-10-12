package com.smartindustry.inventory.vo;

import com.smartindustry.common.bo.im.MaterialInventoryBO;
import com.smartindustry.common.bo.si.LocationBO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
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
    private Long ssid;
    private String mno;
    private String mname;
    private Byte mtype;
    private String mmodel;
    private String mdesc;
    private String supplier;
    private String warehouse;
    private Integer wnum;
    private String wnummunit;
    private BigDecimal llimit;
    private String llimitmunit;
    private Byte status;
    private Integer lnum;
    private String lnummunit;
    private Integer rnum;
    private String rnummunit;
    private Integer anum;
    private String anummunit;
    private String munit;

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
        vo.setSsid(bo.getSafeStockId());
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setMtype(bo.getMaterialType());
        vo.setMmodel(bo.getMaterialModel());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setSupplier(bo.getSupplierName());
        if (null != bo.getLocations() && bo.getLocations().size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (LocationBO lbo : bo.getLocations()) {
                sb.append(lbo.getWarehouseName()).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            vo.setWarehouse(sb.toString());
        }
        String munit = bo.getMeasureUnitName() != null ? (" " + bo.getMeasureUnitName()) : "";
        vo.setMunit(bo.getMeasureUnitName());
        vo.setWnum(bo.getWayNum());
        if (bo.getWayNum() != null) {
            vo.setWnummunit(bo.getWayNum() + munit);
        }
        vo.setLlimit(bo.getLowerLimit());
        if (bo.getLowerLimit() != null) {
            vo.setLlimitmunit(bo.getLowerLimit() + munit);
        }
        vo.setStatus(bo.getStatus());
        vo.setLnum(bo.getLockNum());
        if (bo.getLockNum() != null) {
            vo.setLnummunit(bo.getLockNum() + munit);
        }
        vo.setRnum(bo.getRelateNum());
        if (bo.getRelateNum() != null) {
            vo.setRnummunit(bo.getRelateNum() + munit);
        }
        vo.setAnum(bo.getAvailableNum());
        if (bo.getAvailableNum() != null) {
            vo.setAnummunit(bo.getAvailableNum() + munit);
        }

        return vo;
    }
}
