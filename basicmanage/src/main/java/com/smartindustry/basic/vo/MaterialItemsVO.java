package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.BomHeadBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:25 2020/8/13
 * @version: 1.0.0
 * @description:
 */
@Data
public class MaterialItemsVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 主物料id
     */
    private Long bhid;
    /**
     * 物料ID
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
     * 物料属性
     */
    private String mpname;
    /**
     * 子物料列表
     */
    private List<MaterialItemsVO> children;

    public static List<MaterialItemsVO> convert(List<BomHeadBO> bos) {
        List<MaterialItemsVO> vos = new ArrayList<>(bos.size());
        for (BomHeadBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static MaterialItemsVO convert(BomHeadBO bo) {
        MaterialItemsVO vo = new MaterialItemsVO();
        vo.setBhid(bo.getBomHeadId());
        vo.setMmodel(bo.getMaterialModel());
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setMid(bo.getMaterialId());
        vo.setMpname(bo.getMaterialPropertyName());
        return vo;
    }
}
