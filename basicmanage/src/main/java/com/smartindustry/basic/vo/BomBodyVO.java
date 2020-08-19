package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.BomBodyBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:45 2020/8/14
 * @version: 1.0.0
 * @description:
 */
@Data
public class BomBodyVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 物料配比明细id
     */
    private Long bbid;
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
     * 物料属性id
     */
    private Long mpid;
    /**
     * 物料属性
     */
    private String mpname;
    /**
     * 供应商名称
     */
    private String sname;
    /**
     * 需求量类型
     */
    private Byte mdtype;
    /**
     * 需求量
     */
    private Float mdemand;
    /**
     * 计量单位名称
     */
    private String muname;
    /**
     * 损耗类型
     */
    private Byte mltype;
    /**
     * 损耗
     */
    private Float mloss;
    /**
     * 工序id
     */
    private Long prid;
    /**
     * 工序名称
     */
    private String prname;
    /**
     * 上级物料id
     */
    private Long pid;
    /**
     * 上级物料编号
     */
    private String pname;

    public static List<BomBodyVO> convert(List<BomBodyBO> bos) {
        List<BomBodyVO> vos = new ArrayList<>(bos.size());
        for (BomBodyBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static BomBodyVO convert(BomBodyBO bo) {
        BomBodyVO vo = new BomBodyVO();
        vo.setBbid(bo.getBomBodyId());
        vo.setMno(bo.getMaterialNo());
        vo.setMid(bo.getMaterialId());
        vo.setMname(bo.getMaterialName());
        vo.setMmodel(bo.getMaterialModel());
        vo.setMpid(bo.getMaterialPropertyId());
        vo.setMpname(bo.getMaterialPropertyName());
        vo.setSname(bo.getSupplierName());
        vo.setMdemand(bo.getMaterialDemand());
        vo.setMuname(bo.getMeasureUnitName());
        vo.setMloss(bo.getMaterialLoss());
        vo.setPrid(bo.getProcessId());
        vo.setPrname(bo.getProcessName());
        vo.setPid(bo.getParentId());
        vo.setPname(bo.getParentName());
        vo.setMdtype(bo.getDemandType());
        vo.setMltype(bo.getLossType());
        return vo;
    }

}
