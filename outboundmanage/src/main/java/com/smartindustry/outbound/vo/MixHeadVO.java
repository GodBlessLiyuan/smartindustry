package com.smartindustry.outbound.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartindustry.common.bo.om.MixBodyBO;
import com.smartindustry.common.bo.om.MixHeadBO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:22 2020/10/28
 * @version: 1.0.0
 * @description:
 */
@Data
public class MixHeadVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 混料工单id
     */
    private Long mixid;
    /**
     * 混料工单号
     */
    private String mixno;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ptime;

    private List<MixBodyVO> vos;

    @Data
    public static class MixBodyVO {
        /**
         * 表体id
         */
        private Long mbid;
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
         * 计划出库数量
         */
        private BigDecimal pnum;
        /**
         * 物料的计量单位
         */
        private String muname;
    }

    public static List<MixHeadVO> convert(List<MixHeadBO> bos) {
        List<MixHeadVO> vos = new ArrayList<>(bos.size());
        for (MixHeadBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static MixHeadVO convert(MixHeadBO bo) {
        MixHeadVO vo = new MixHeadVO();
        vo.setMixid(bo.getSlurryId());
        vo.setMixno(bo.getSlurryNo());
        vo.setPtime(bo.getPlanDate());
        List<MixBodyVO> vos = new ArrayList<>();
        for(MixBodyBO bodyBO : bo.getBos()){
            MixBodyVO bodyVO = new MixBodyVO();
            bodyVO.setMbid(bodyBO.getSlurryMaterialId());
            bodyVO.setMid(bodyBO.getMaterialId());
            bodyVO.setMno(bodyBO.getMaterialNo());
            bodyVO.setMmodel(bodyBO.getMaterialModel());
            bodyVO.setMname(bodyBO.getMaterialName());
            bodyVO.setPnum(bodyBO.getPlanNum());
            bodyVO.setMuname(bodyBO.getMeasureUnitName());
            vos.add(bodyVO);
        }
        vo.setVos(vos);
        return vo;
    }
}
