package com.smartindustry.pda.vo;

import com.smartindustry.common.bo.om.OutboundBodyBO;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/10/31 11:34
 * @description: TODO
 * @version: 1.0
 */
@Data
public class OutboundListVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 出库单ID
     */
    private Long ohid;
    /**
     * 单号
     */
    private String sno;
    /**
     * 出库物料
     */
    private String mname;
    /**
     * 规格参数
     */
    private String mmodel;
    /**
     * 需出库量
     */
    private String dnum;
    /**
     * 入库数
     */
    private String onum;

    public static List<OutboundListVO> convert(List<OutboundHeadBO> bos) {
        List<OutboundListVO> vos = new ArrayList<>(bos.size());
        for (OutboundHeadBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    private static OutboundListVO convert(OutboundHeadBO bo) {
        OutboundListVO vo = new OutboundListVO();
        vo.setOhid(bo.getOutboundHeadId());
        vo.setSno(bo.getSourceNo());
        vo.setDnum(null);
        vo.setOnum(null);

        if (null != bo.getBodyBOs() && bo.getBodyBOs().size() > 0) {
            OutboundBodyBO bodyBO = bo.getBodyBOs().get(0);
            vo.setMname(bodyBO.getMaterialName());
            vo.setMmodel(bodyBO.getMaterialModel());
        }

        return vo;
    }
}
