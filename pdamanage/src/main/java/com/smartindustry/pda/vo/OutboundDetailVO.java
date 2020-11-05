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
 * @date: Created in 2020/10/31 14:39
 * @description: 成品出库
 * @version: 1.0
 */
@Data
public class OutboundDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String[] COLORS = new String[]{"#FFFF00", "#FFFAFA", "#FF0000", "#00FF00"};

    /**
     * 出库单ID
     */
    private Long ohid;
    /**
     * 单号
     */
    private String sno;
    /**
     * 物料
     */
    private List<MaterialVO> mvos;
    /**
     * 需出库量
     */
    private BigDecimal dnum;
    /**
     * 入库数
     */
    private BigDecimal onum;
    /**
     * 叉车
     */
    private List<String> fnames;
    /**
     * 状态：开始执行；辅助执行；关闭;消失
     */
    private String status;
    /**
     * 储位图
     */
    private List<LocationVO> lvos;
    /**
     * 当前排位
     */
    private BigDecimal cnum;

    public static OutboundDetailVO convert(OutboundHeadBO bo) {
        OutboundDetailVO vo = new OutboundDetailVO();
        vo.setOhid(bo.getOutboundHeadId());
        vo.setSno(bo.getSourceNo());
        vo.setDnum(bo.getExpectNum());
        vo.setOnum(bo.getOutboundNum());
        if (null != bo.getBodyBOs() && bo.getBodyBOs().size() > 0) {
            List<MaterialVO> mvos = new ArrayList<>(bo.getBodyBOs().size());
            for (OutboundBodyBO bodyBO : bo.getBodyBOs()) {
                MaterialVO mvo = new MaterialVO();
                mvo.setMname(bodyBO.getMaterialName());
                mvo.setMmodel(bodyBO.getMaterialModel());
                mvos.add(mvo);
            }
            vo.setMvos(mvos);
        }
        return vo;
    }

    @Data
    private static class MaterialVO {
        /**
         * 出库物料
         */
        private String mname;
        /**
         * 规格参数
         */
        private String mmodel;
    }

    @Data
    public static class LocationVO {
        /**
         * 物料信息： 物料编码 + 物料规格
         */
        private String minfo;
        /**
         * 颜色
         */
        private String color;
        /**
         * 储位RFID
         */
        private List<String> lrfids = new ArrayList<>();
    }
}
