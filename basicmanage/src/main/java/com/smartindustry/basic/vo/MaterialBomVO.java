package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.MaterialBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:57 2020/8/14
 * @version: 1.0.0
 * @description:
 */
@Data
public class MaterialBomVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    private Long mid;
    private String mno;
    private Long mtid;
    private String mname;
    private String mmodel;
    private String mdesc;
    private String sname;
    private String muname;

    public static List<MaterialBomVO> convert(List<MaterialBO> bos) {
        List<MaterialBomVO> vos = new ArrayList<>(bos.size());
        for (MaterialBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static MaterialBomVO convert(MaterialBO bo) {
        MaterialBomVO vo = new MaterialBomVO();
        vo.setMid(bo.getMaterialId());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setMmodel(bo.getMaterialModel());
        vo.setMno(bo.getMaterialNo());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setSname(bo.getSupplierName());
        vo.setMuname(bo.getMeasureUnitName());
        vo.setMname(bo.getMaterialName());
        return vo;
    }
}
