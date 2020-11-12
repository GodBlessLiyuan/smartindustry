package com.smartindustry.inventory.vo;

import com.smartindustry.common.bo.si.MaterialInventoryBO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: jiangchaojie
 * @date: Created in 2020/11/11
 * @description: 物料库存统计
 * @version: 1.0
 */
@Data
public class MaterialInventoryVO implements Serializable {
    private static final long SerialVersionUID = 1L;
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
     * 物料类型
     */
    private Byte mtype;
    /**
     * 物料型号
     */
    private String mmodel;
    /**
     * 物料描述
     */
    private String mdesc;
    /**
     * 供应商名称
     */
    private String sname;
    /**
     * 仓库名称
     */
    private String wname;
    /**
     * 安全库存上限
     */
    private BigDecimal upper;
    /**
     * 安全库存下限
     */
    private BigDecimal lower;
    /**
     * 库存状态
     */
    private Byte status;
    /**
     * 库存数量
     */
    private BigDecimal num;
    /**
     * 计量单位
     */
    private String muname;

    public static List<MaterialInventoryVO> convert(List<MaterialInventoryBO> bos) {
        List<MaterialInventoryVO> vos = new ArrayList<>(bos.size());
        for (MaterialInventoryBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static MaterialInventoryVO convert(MaterialInventoryBO bo) {
        MaterialInventoryVO vo = new MaterialInventoryVO();
        vo.setMid(bo.getMaterialId());
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setMtype(bo.getMaterialType());
        vo.setMmodel(bo.getMaterialModel());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setSname(bo.getSupplierName());
        vo.setWname(bo.getWarehouseName());
        vo.setUpper(bo.getUpperLimit());
        vo.setLower(bo.getLowerLimit());
        if(bo.getInventoryQuantity()!=null && bo.getPackageVolume()!=null){
            vo.setNum(bo.getInventoryQuantity().multiply(bo.getPackageVolume()));
        }
        vo.setMuname(bo.getMeasureUnitName());
        vo.setStatus(bo.getStatus());
        return vo;
    }
}
