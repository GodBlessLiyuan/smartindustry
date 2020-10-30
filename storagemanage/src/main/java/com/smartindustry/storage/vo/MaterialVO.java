package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.si.MaterialBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:11 2020/10/27
 * @version: 1.0.0
 * @description:
 */
@Data
public class MaterialVO implements Serializable {
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
     * 物料型号
     */
    private String mmodel;
    /**
     * 供应商
     */
    private String sname;
    /**
     * 物料计量单位
     */
    private String muname;

    public static List<MaterialVO> convert(List<MaterialBO> bos) {
        List<MaterialVO> vos = new ArrayList<>(bos.size());
        for (MaterialBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static MaterialVO convert(MaterialBO bo) {
        MaterialVO vo = new MaterialVO();
        vo.setMid(bo.getMaterialId());
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setMmodel(bo.getMaterialModel());
        vo.setSname(bo.getSupplierName());
        vo.setMuname(bo.getMeasureUnitName());
        return vo;
    }
}
