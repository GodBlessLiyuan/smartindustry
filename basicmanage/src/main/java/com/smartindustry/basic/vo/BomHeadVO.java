package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.BomHeadBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:47 2020/8/13
 * @version: 1.0.0
 * @description:
 */
@Data
public class BomHeadVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    private Long mid;

    /**
     * 主bom清单id
     */
    private Long bhid;
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
     * 物料描述
     */
    private String mdesc;
    /**
     * 物料层级名称
     */
    private String mlname;
    /**
     * 物料关联数量
     */
    private Integer rnum;





    public static List<BomHeadVO> convert(List<BomHeadBO> bos) {
        List<BomHeadVO> vos = new ArrayList<>(bos.size());
        for (BomHeadBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static BomHeadVO convert(BomHeadBO bo) {
        BomHeadVO vo = new BomHeadVO();
        vo.setMdesc(bo.getMaterialDesc());
        vo.setMlname(bo.getMaterialLevelName());
        vo.setMmodel(bo.getMaterialModel());
        vo.setMno(bo.getMaterialNo());
        vo.setRnum(bo.getRelateNum());
        vo.setMname(bo.getMaterialName());
        vo.setMid(bo.getMaterialId());
        vo.setBhid(bo.getBomHeadId());
        return vo;
    }
}
