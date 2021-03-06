package com.smartindustry.outbound.vo;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartindustry.common.bo.om.OutboundBodyBO;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.outbound.constant.OutboundConstant;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:02 2020/10/28
 * @version: 1.0.0
 * @description:
 */
@Data
public class OutboundHeadVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 出库单表头id
     */
    private Long ohid;
    /**
     * 出库单编码
     */
    private String ono;

    private Long mixid;
    /**
     * 混料工单号
     */
    private String mixno;
    /**
     * 生产工单号
     */
    private String pno;

    private String sno;
    /**
     * 计划出库时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ptime;
    /**
     * 完成出库时间
     */
    private Date otime;
    /**
     * 单据状态
     */
    private Byte status;

    private Byte type;

    private String extra;
    /**
     * 客户名称
     */
    private String cname;
    /**
     * 接受地址
     */
    private String address;

    private List<OutboundBodyVO> vos;

    @Data
    public static class OutboundBodyVO {
        /**
         * 表体id
         */
        private Long obid;

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
         * 计划出库数量
         */
        private BigDecimal pnum;
        /**
         * 出库数量
         */
        private BigDecimal onum;
        /**
         * 出库时间
         */
        private Date otime;
        /**
         * 物料计量单位
         */
        private String muname;
    }

    public static List<OutboundHeadVO> convert(List<OutboundHeadPO> pos) {
        List<OutboundHeadVO> vos = new ArrayList<>(pos.size());
        for (OutboundHeadPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static OutboundHeadVO convert(OutboundHeadPO po) {
        OutboundHeadVO vo = new OutboundHeadVO();
        vo.setOhid(po.getOutboundHeadId());
        vo.setOno(po.getOutboundNo());
        if(po.getSourceType().equals(OutboundConstant.TYPE_INSERT_BY_OURS)){
            vo.setMixno(po.getSourceNo());
        }else {
            vo.setPno(po.getSourceNo());
        }
        vo.setPtime(po.getPlanTime());
        vo.setOtime(po.getOutboundTime());
        vo.setStatus(po.getStatus());
        vo.setType(po.getSourceType());
        return vo;
    }


    public static List<OutboundHeadVO> convertBO(List<OutboundHeadBO> bos) {
        List<OutboundHeadVO> vos = new ArrayList<>(bos.size());
        for (OutboundHeadBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static OutboundHeadVO convert(OutboundHeadBO bo) {
        OutboundHeadVO vo = new OutboundHeadVO();
        vo.setOhid(bo.getOutboundHeadId());
        vo.setOno(bo.getOutboundNo());
        vo.setMixid(bo.getMixHeadId());
        vo.setMixno(bo.getMixNo());
        vo.setPtime(bo.getPlanTime());
        vo.setExtra(bo.getExtra());
        vo.setSno(bo.getSourceNo());
        vo.setOtime(bo.getOutboundTime());
        vo.setStatus(bo.getStatus());
        vo.setAddress(bo.getAcceptAddress());
        vo.setCname(bo.getClientName());
        List<OutboundBodyVO> vos = new ArrayList<>();
        if(bo.getBodyBOs() != null){
            for (OutboundBodyBO bodyBO : bo.getBodyBOs()){
                OutboundBodyVO bodyVO = new OutboundBodyVO();
                bodyVO.setObid(bodyBO.getOutboundBodyId());
                bodyVO.setMid(bodyBO.getMaterialId());
                bodyVO.setMno(bodyBO.getMaterialNo());
                bodyVO.setMname(bodyBO.getMaterialName());
                bodyVO.setMmodel(bodyBO.getMaterialModel());
                bodyVO.setOnum(bodyBO.getOutboundNum());
                bodyVO.setOtime(bodyBO.getOutboundTime());
                bodyVO.setPnum(bodyBO.getPlanNum());
                bodyVO.setMuname(bodyBO.getMeasureUnitName());
                vos.add(bodyVO);
            }
        }
        vo.setVos(vos);
        return vo;
    }
}
