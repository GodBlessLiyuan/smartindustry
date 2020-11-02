package com.smartindustry.pda.vo;

import com.smartindustry.common.bo.om.OutboundBodyBO;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/10/31 11:34
 * @description: 列表区数据
 * @version: 1.0
 */
@Data
public class PdaListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型：2-入库在前，3-出库在前
     */
    private Byte type;
    /**
     * 入库
     */
    private List<ListVO> slist;
    /**
     * 出库
     */
    private List<ListVO> olist;

    public void setOlist(List<OutboundHeadBO> bos) {
        List<ListVO> vos = new ArrayList<>(bos.size());
        for (OutboundHeadBO bo : bos) {
            vos.add(convert(bo));
        }
        olist = vos;
    }

    private static ListVO convert(OutboundHeadBO bo) {
        ListVO vo = new ListVO();
        vo.setHid(bo.getOutboundHeadId());
        vo.setSno(bo.getSourceNo());
        vo.setDnum(bo.getExpectNum());
        vo.setSnum(bo.getOutboundNum());

        if (null != bo.getBodyBOs() && bo.getBodyBOs().size() > 0) {
            OutboundBodyBO bodyBO = bo.getBodyBOs().get(0);
            vo.setMname(bodyBO.getMaterialName());
            vo.setMmodel(bodyBO.getMaterialModel());
        }

        return vo;
    }


    @Data
    private static class ListVO {
        /**
         * 出/入库单ID
         */
        private Long hid;
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
         * 需出/入库量
         */
        private BigDecimal dnum;
        /**
         * 出/入库数
         */
        private BigDecimal snum;
    }
}
