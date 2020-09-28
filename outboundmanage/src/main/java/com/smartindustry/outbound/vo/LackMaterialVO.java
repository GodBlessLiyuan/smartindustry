package com.smartindustry.outbound.vo;

import com.smartindustry.common.bo.om.PickBodyBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:30 2020/7/16
 * @version: 1.0.0
 * @description:
 */
@Data
public class LackMaterialVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    private Long pbid;
    /**
     * 物料编号
     */
    private String mno;
    /**
     * 物料名称
     */
    private String mname;
    /**
     * 物料描述
     */
    private String mdesc;
    /**
     * 需求数量
     */
    private Integer dnum;

    /**
     * 需求数量 计量单位
     */
    private String dnummunit;
    /**
     * 已扫描数量
     */
    private Integer pnum;

    /**
     * 已扫描数量+计量单位
     */
    private String pnummunit;
    /**
     * 欠料的数量
     */
    private Integer lnum;

    /**
     * 欠料数量+计量单位
     */
    private String lnummunit;

    private Byte flag;

    public static List<LackMaterialVO> convert(List<PickBodyBO> bos) {
        List<LackMaterialVO> vos = new ArrayList<>(bos.size());
        for (PickBodyBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static LackMaterialVO convert(PickBodyBO bo) {
        LackMaterialVO vo = new LackMaterialVO();
        vo.setPbid(bo.getPickBodyId());
        vo.setDnum(bo.getDemandNum());
        vo.setLnum(bo.getLackNum());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setMname(bo.getMaterialName());
        vo.setMno(bo.getMaterialNo());
        vo.setPnum(bo.getPickNum());
        vo.setFlag(bo.getFlag());
        String mname = bo.getMeasureUnitName() != null? bo.getMeasureUnitName():"";
        if (bo.getDemandNum() != null) {
            vo.setDnummunit(bo.getDemandNum()+" "+mname);
        }
        if (bo.getPickNum() != null) {
            vo.setPnummunit(bo.getPickNum()+" "+mname);
        }
        if (bo.getLackNum() != null) {
            vo.setLnummunit(bo.getLackNum() +" "+mname);
        }
        return vo;
    }
}
